package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpExtensionType
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpConvertRequest
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpFileConvertRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
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
    private val fileStoragePort: FileStoragePort
) {
    @PostMapping("/json-to-hwp")
    fun makeHwpFile(
        @RequestBody
        request: HwpConvertRequest
    ): ResponseEntity<ResponseData<Any>> {
        val hwpByteArr = hwpSocketClient.requestHwpFile(request.jsonMsg)
        return ResponseUtil.ok(mapOf("hwpFile" to Base64.getEncoder().encodeToString(hwpByteArr)))
    }

    @PostMapping("/hwp-to-html")
    fun makeHtml(
        @ModelAttribute @Valid
        request: HwpFileConvertRequest
    ): ResponseEntity<ResponseData<Any>> {
        val hwpFile = request.hwpFile
        val extension = hwpFile.originalFilename!!.split(".").last()
        val extensionType = HwpExtensionType.valueOf(extension.uppercase())
        val zipByteArr = hwpSocketClient.requestHtmlZip(hwpFile.inputStream, hwpFile.size.toInt(), extensionType)

        /**
         * todo
         * 1. unzip
         * 2. /bindata 하위 이미지 파일 s3 저장
         * 3. /index.xhtml 한줄씩 읽어들여 문자열로 컨텐츠 저장 -> hwp_convert_content에 영속화
         * 4. s3FileUrl, contentsList(나의 변환 컨텐츠 목록)
         */
        // 1. unZip
        val unZipFile = unzipAndProcess(zipByteArr)
        val indexHtml = unZipFile.first
        val imageFiles = unZipFile.second

        // 2. s3에 이미지 저장
        val currentTime1 = System.currentTimeMillis()
        val randomValue1: Int = Random().nextInt(100)
        val filePath = "${currentTime1}_${randomValue1}"
        imageFiles.forEach {
            fileStoragePort.upload(
                FileUploadDto(
                    name = "$filePath/bindata/${it.key}",
                    contentType = "image/png",
                    size = it.value.size.toLong(),
                    inputStream = it.value.inputStream()
                )
            )
        }

        // 3. hwp_convert_content에 영속화
        val htmlString = indexHtml!!.lineSequence().joinToString("")

        return ResponseUtil.ok(mapOf("zipFile" to Base64.getEncoder().encodeToString(zipByteArr)))
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

    fun readIndexXhtml(indexXhtmlBytes: ByteArray): String {
        // ByteArrayInputStream으로 ByteArray를 스트림으로 변환
        val inputStream = ByteArrayInputStream(indexXhtmlBytes)

        // BufferedReader를 사용해 한 줄씩 읽기
        BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
            val stringBuilder = StringBuilder()

            // 한 줄씩 읽어 StringBuilder에 추가
            reader.lineSequence().forEach { line ->
                stringBuilder.append(line).append("\n")
            }

            return stringBuilder.toString().trimEnd() // 마지막 줄바꿈 제거
        }
    }
}