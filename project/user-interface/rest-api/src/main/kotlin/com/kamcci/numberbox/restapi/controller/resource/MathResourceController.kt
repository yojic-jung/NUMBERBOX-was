package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadUseCase
import com.kamcci.numberbox.restapi.dto.response.common.PageResponseImpl.Companion.paginate
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/math/resource")
@RestController
class MathResourceController(
    private val mathResourceReadUseCase: MathResourceReadUseCase
) {

    @GetMapping("/{mainCateId}")
    fun read(
        @PathVariable
        mainCateId: Int,
        @ModelAttribute
        pageReq: PageRequestImpl
    ): ResponseEntity<ResponseData<Any>> {
        val contents = mathResourceReadUseCase.readByMainCateId(mainCateId, pageReq)
        val rs = paginate(contents, pageReq) { mathResourceReadUseCase.countByMainCateId(mainCateId) }
        return ResponseUtil.ok(rs)
    }
}