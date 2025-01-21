package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.numberbox.app.domain.dto.hwp.HwpExtensionType
import com.kamcci.numberbox.app.port.hwp.HwpSocketClient
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpConvertRequest
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpFileConvertRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * 한글 파일 변환 컨트롤러
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/hwp/convert")
class HwpConvertController(
    private val hwpSocketClient: HwpSocketClient
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
        return ResponseUtil.ok(mapOf("zipFile" to Base64.getEncoder().encodeToString(zipByteArr)))
    }
}