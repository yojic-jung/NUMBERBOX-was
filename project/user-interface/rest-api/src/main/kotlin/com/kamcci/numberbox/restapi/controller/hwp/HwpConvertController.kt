package com.kamcci.numberbox.restapi.controller.hwp

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 한글 파일 변환 컨트롤러
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/hwp")
class HwpConvertController {
//    @PostMapping("")
//    fun makeHwpFile(): ResponseEntity<ResponseData<Any>> {
//
//    }
}