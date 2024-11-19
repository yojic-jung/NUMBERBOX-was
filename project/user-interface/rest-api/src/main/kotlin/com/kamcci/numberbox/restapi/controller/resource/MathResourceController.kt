package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceModifyUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadUseCase
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceCreateRequest
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceUpdateRequest
import com.kamcci.numberbox.restapi.dto.response.common.PageResponseImpl.Companion.paginate
import com.kamcci.numberbox.restapi.mapper.resource.MathResourceMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RequestMapping("/math/resource")
@RestController
class MathResourceController(
    private val mathResourceMenuReadUseCase: MathResourceMenuReadUseCase,
    private val mathResourceReadUseCase: MathResourceReadUseCase,
    private val mathResourceModifyUseCase: MathResourceModifyUseCase,
    private val mathResourceMapper: MathResourceMapper
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
        val contents = mathResourceReadUseCase.readByMainCateId(mainCateId, pageReq)
        val rs = paginate(contents, pageReq) { mathResourceReadUseCase.countByMainCateId(mainCateId) }
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
        val contents = mathResourceReadUseCase.readByMemberId(memberId, pageReq)
        val rs = paginate(contents, pageReq) { mathResourceReadUseCase.countByMemberId(memberId) }

        // 카테고리 메뉴
        val menuList = mathResourceMenuReadUseCase.readAll()

        return ResponseUtil.ok(
            mapOf(
                "resource" to rs,
                "menu" to menuList
            )
        )
    }

    /**
     * 등록
     */
    @PostMapping
    fun save(
        @UserId
        memberId: UUID,
        @ModelAttribute @Valid
        request: MathResourceCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 학습 자료 영속화
        val createDto = mathResourceMapper.toDto(memberId, request)
        val resourceId = mathResourceModifyUseCase.create(createDto)
        return ResponseUtil.ok(resourceId)
    }

    /**
     * 수정
     */
    @PutMapping
    fun update(
        @UserId
        memberId: UUID,
        @ModelAttribute @Valid
        request: MathResourceUpdateRequest
    ): ResponseEntity<ResponseData<Any>> {
        val updateDto = mathResourceMapper.toDto(memberId, request)
        mathResourceModifyUseCase.update(updateDto)

        val updatedVo = mathResourceReadUseCase.readById(updateDto.resourceId)
        return ResponseUtil.ok(updatedVo)
    }
}