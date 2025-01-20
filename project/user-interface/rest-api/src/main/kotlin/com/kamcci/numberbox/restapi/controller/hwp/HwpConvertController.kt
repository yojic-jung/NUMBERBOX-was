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
        return ResponseUtil.ok(mapOf("zipFile" to Base64.getEncoder().encodeToString(zipByteArr)))
    }
}