package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceModifyUseCase
import com.kamcci.numberbox.app.usecase.resource.MathResourceReadUseCase
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceCreateRequest
import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceUpdateRequest
import com.kamcci.numberbox.restapi.dto.response.common.PageResponseImpl.Companion.paginate
import com.kamcci.numberbox.restapi.util.file.FileConvertUtil
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
    private val mathResourceModifyUseCase: MathResourceModifyUseCase
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

    @PostMapping
    fun save(
        @UserId
        memberId: UUID,
        @ModelAttribute @Valid
        request: MathResourceCreateRequest
    ): ResponseEntity<ResponseData<Any>> {
        // ppt 슬라이드 이미지 추출
        val slideImgList = FileConvertUtil.pptToImg(request.pptFile)

        // 학습 자료 영속화
        val createDto = MathResourceCreateDto(
            memberId = memberId,
            title = request.title,
            mainCateId = request.mainCateId,
            midCateId = request.midCateId,
            cateList = request.cateList,
            pptFileOriginalName = request.pptFile.originalFilename ?: "tmp.ppt",
            pptFile = request.pptFile.inputStream,
            slideImgList = slideImgList,
            imgFileOriginalName = request.imgFile?.originalFilename,
            imgFile = request.imgFile?.inputStream,
        )
        val resourceId = mathResourceModifyUseCase.create(createDto)
        return ResponseUtil.ok(resourceId)
    }

    @PutMapping
    fun update(
        @UserId
        memberId: UUID,
        @ModelAttribute @Valid
        request: MathResourceUpdateRequest
    ): ResponseEntity<ResponseData<String>> {
        // ppt 슬라이드 이미지 추출
        val slideImgList = if (request.pptFile != null) {
            FileConvertUtil.pptToImg(request.pptFile)
        } else listOf()

        val updateDto = MathResourceUpdateDto(
            memberId = memberId,
            title = request.title,
            mainCateId = request.mainCateId,
            midCateId = request.midCateId,
            cateList = request.cateList,
            pptFileOriginalName = request.pptFile?.originalFilename,
            pptFile = request.pptFile?.inputStream,
            slideImgList = slideImgList,
            imgFileOriginalName = request.imgFile?.originalFilename,
            imgFile = request.imgFile?.inputStream,
        )
        mathResourceModifyUseCase.update(updateDto)
        return ResponseUtil.ok()
    }
}