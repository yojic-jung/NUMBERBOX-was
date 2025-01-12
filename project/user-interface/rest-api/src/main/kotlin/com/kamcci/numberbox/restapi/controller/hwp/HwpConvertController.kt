package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.numberbox.app.usecase.hwp.HwpFileConvertCase
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpConvertRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 한글 파일 변환 컨트롤러
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/hwp/math/docs")
class HwpConvertController(
    private val hwpFileConvertCase: HwpFileConvertCase
) {
    @PostMapping("")
    fun makeHwpFile(
        @RequestBody
        request: HwpConvertRequest
    ): ResponseEntity<ResponseData<Any>> {
        val hwpByteArr = hwpFileConvertCase.convertJsonMsgToHwp(request.jsonMsg)
        return ResponseUtil.ok(mapOf("hwpFile" to hwpByteArr))
    }
}