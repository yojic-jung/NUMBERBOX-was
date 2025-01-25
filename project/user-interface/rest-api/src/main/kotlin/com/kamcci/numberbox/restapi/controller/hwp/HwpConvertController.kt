package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertContentsUpdateDto
import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsReadCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertContentsWriteCase
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpConvertRequest
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpFileConvertRequest
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpToHtmlUpdateRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * 한글 파일 변환 컨트롤러
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/hwp/convert")
class HwpConvertController(
    private val hwpSocketClient: HwpSocketClient,
    private val fileStoragePort: FileStoragePort,
    private val hwpConvertContentsWriteCase: HwpConvertContentsWriteCase,
    private val hwpConvertContentsReadCase: HwpConvertContentsReadCase,
) {
    companion object {
        const val NOT_MODIFIED = "수정 및 삭제 작업이 이루어지지 않았습니다."
    }

    // json 문자열 to hwp 파일 변환
    @PostMapping("/json-to-hwp")
    fun makeHwpFile(
        @RequestBody
        request: HwpConvertRequest
    ): ResponseEntity<ResponseData<Any>> {
        val hwpByteArr = hwpSocketClient.requestHwpFile(request.jsonMsg)
        return ResponseUtil.ok(mapOf("hwpFile" to Base64.getEncoder().encodeToString(hwpByteArr)))
    }

    // hwp to html변환 및 컨텐츠 저장
    @PostMapping("/hwp-to-html")
    fun makeHtml(
        @UserId memberId: UUID,
        @ModelAttribute @Valid
        request: HwpFileConvertRequest
    ): ResponseEntity<ResponseData<Any>> {
        val hwpFile = request.hwpFile
        val extension = hwpFile.originalFilename!!.split(".").last()
        val extensionType = HwpExtensionType.valueOf(extension.uppercase())
        val zipByteArr = hwpSocketClient.requestHtmlZip(hwpFile.inputStream, hwpFile.size.toInt(), extensionType)

        // 1. unZip
        val unZipFile = unzipAndProcess(zipByteArr)
        val indexHtml = unZipFile.first
        val imageFiles = unZipFile.second

        // 2. s3에 이미지 저장
        val now = LocalDate.now()
        val currentTime1 = System.currentTimeMillis()
        val randomValue1: Int = Random().nextInt(100)
        val imgFilePath = "docs/${now.year}/${now.month}/${currentTime1}_${randomValue1}"
        imageFiles.forEach {
            fileStoragePort.upload(
                FileUploadDto(
                    name = "$imgFilePath/${it.key}",
                    contentType = "image/png",
                    size = it.value.size.toLong(),
                    inputStream = it.value.inputStream()
                )
            )
        }

        // 3. hwp_convert_content에 영속화
        val htmlString = indexHtml!!.lineSequence().joinToString("")
        val createDto = HwpConvertContentsCreateDto(
            memberId = memberId,
            isConverted = true,
            fileName = hwpFile.originalFilename ?: "파일명없음.hwp",
            contents = htmlString,
            imgPath = imgFilePath,
        )
        hwpConvertContentsWriteCase.create(createDto)

        // 4. 변환 컨텐츠 조회
        val contentsList = hwpConvertContentsReadCase.readAllByMemberId(memberId)

        return ResponseUtil.ok(
            mapOf(
                "contentsList" to contentsList,
                "s3FileUrl" to contentsList[0].imgPath
            )
        )
    }

    // 변환 컨텐츠 수정사항 저장
    @PutMapping("/hwp-to-html")
    fun update(
        @UserId memberId: UUID,
        @RequestBody
        request: HwpToHtmlUpdateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 변환 컨텐츠 수정
        hwpConvertContentsWriteCase.update(
            HwpConvertContentsUpdateDto(
                id = request.id,
                memberId = memberId,
                contents = request.contents,
                isGrammarConverted = true
            )
        ).let { if (it != 1L) throw BusinessInValidException(NOT_MODIFIED) }

        // 변환 컨텐츠 조회
        val contentsList = hwpConvertContentsReadCase.readAllByMemberId(memberId)
        return ResponseUtil.ok(mapOf("contentsList" to contentsList))
    }

    // 변환 컨텐츠 수정사항 저장
    @DeleteMapping("/hwp-to-html/{contentsId}")
    fun delete(
        @UserId memberId: UUID,
        @PathVariable contentsId: Long
    ): ResponseEntity<ResponseData<Any>> {
        // 변환 컨텐츠 수정
        hwpConvertContentsWriteCase.delete(contentsId, memberId)
            .let { if (it != 1L) throw BusinessInValidException(NOT_MODIFIED) }

        // 변환 컨텐츠 조회
        val contentsList = hwpConvertContentsReadCase.readAllByMemberId(memberId)
        return ResponseUtil.ok(mapOf("contentsList" to contentsList))
    }

    fun unzipAndProcess(zipBytes: ByteArray): Pair<String?, Map<String, ByteArray>> {
        var indexXhtml: String? = null
        val images = mutableMapOf<String, ByteArray>()

        // ByteArrayInputStream으로 byteArray 읽기
        val byteArrayInputStream = ByteArrayInputStream(zipBytes)

        // ZipInputStream으로 압축 해제
        ZipInputStream(byteArrayInputStream).use { zipInputStream ->
            var entry: ZipEntry? = zipInputStream.nextEntry

            while (entry != null) {
                val entryName = entry.name

                if (!entry.isDirectory) {
                    when {
                        // index.xhtml 파일 읽기
                        entryName.equals("index.xhtml", ignoreCase = true) -> {
                            indexXhtml = zipInputStream.readBytes().toString(Charsets.UTF_8)
                        }
                        // /bindata/ 하위 이미지 파일 읽기
                        entryName.startsWith("bindata/") -> {
                            val fileName = entryName.removePrefix("bindata/")
                            images[fileName] = zipInputStream.readBytes()
                        }
                    }
                }

                zipInputStream.closeEntry()
                entry = zipInputStream.nextEntry
            }
        }

        return indexXhtml to images
    }

}