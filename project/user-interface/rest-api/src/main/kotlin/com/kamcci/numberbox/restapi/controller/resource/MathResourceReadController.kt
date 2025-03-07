package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import com.kamcci.numberbox.restapi.dto.response.common.PageResponseImpl.Companion.paginate
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


/**
 * 학습자료 조회
 */
@RequestMapping("/math/resource")
@RestController
class MathResourceReadController(
    private val mathResourceMenuReadCase: MathResourceMenuReadCase,
    private val mathResourceReadCase: MathResourceReadCase,
) {
    /**
     * 조회 - 카테고리 id로
     */
    @GetMapping("/{mainCateId}")
    fun read(
        @PathVariable
        mainCateId: Int,
        @ModelAttribute
        pageReq: PageRequestImpl
    ): ResponseEntity<ResponseData<Any>> {
        val contents = mathResourceReadCase.readByMainCateId(mainCateId, pageReq)
        val rs = paginate(contents, pageReq) { mathResourceReadCase.countByMainCateId(mainCateId) }
        return ResponseUtil.ok(rs)
    }

    /**
     * 조회 - 나의 학습 자료
     */
    @GetMapping("/my")
    fun read(
        @UserId
        memberId: UUID,
        @ModelAttribute
        pageReq: PageRequestImpl
    ): ResponseEntity<ResponseData<Any>> {
        // 컨텐츠
        val contents = mathResourceReadCase.readByMemberId(memberId, pageReq)
        val rs = paginate(contents, pageReq) { mathResourceReadCase.countByMemberId(memberId) }

        // 카테고리 메뉴
        val menuList = mathResourceMenuReadCase.readAll()

        return ResponseUtil.ok(
            mapOf(
                "resource" to rs,
                "menu" to menuList
            )
        )
    }
}