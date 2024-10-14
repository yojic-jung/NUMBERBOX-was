package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeModifyUseCase
import com.kamcci.numberbox.app.usecase.math.MathContentsReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoModifyUseCase
import com.kamcci.numberbox.app.usecase.math.MathUnitInfoReadUseCase
import com.kamcci.numberbox.restapi.dto.request.math.ContentsIdRequest
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsSearchRequest
import com.kamcci.numberbox.restapi.util.math.MathUnitUtil.getUnitIdList
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/math/content")
class MathContentsController(
    private val mathUnitInfoReadUseCase: MathUnitInfoReadUseCase,
    private val mathContentsReadUseCase: MathContentsReadUseCase,
    private val mathConRepoModifyUseCase: MathContentsRepoModifyUseCase,
    private val mathConLikeModifyUseCase: MathContentsLikeModifyUseCase,
) {
    // 문제 등록

    
    // 문제 조회
    @GetMapping("/list")
    fun read(
        @UserId memberId: UUID,
        @ModelAttribute
        @Valid req: MathContentsSearchRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 검색할 단원 id 추출
        val unitInfoList = mathUnitInfoReadUseCase.findAll()
        val unitIdList: List<Int> = getUnitIdList(unitInfoList, req.searchType, req.unitId)

        // 문제 조회
        val pageReq = PageRequestImpl(req.pageNum ?: 0, req.pageVolume ?: 100)
        val res = mathContentsReadUseCase.findByUnitId(memberId, unitIdList, pageReq)

        return ResponseUtil.ok(mapOf("contents" to res))
    }


    // 저장소에 문제 저장
    @PostMapping("/repo")
    fun putRepo(
        @UserId userId: UUID,
        @RequestBody @Valid req: ContentsIdRequest
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsRepoModifyDto(req.contentsId, userId)
        mathConRepoModifyUseCase.save(modifyDto)
        return ResponseUtil.ok(true)
    }

    // 저장소에서 삭제
    @DeleteMapping("/repo")
    fun deleteRepo(
        @UserId userId: UUID,
        @RequestBody @Valid req: ContentsIdRequest
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsRepoModifyDto(req.contentsId, userId)
        mathConRepoModifyUseCase.delete(modifyDto)
        return ResponseUtil.ok(true)
    }

    // 문제 좋아요
    @PostMapping("/like")
    fun likeContents(
        @UserId userId: UUID,
        @RequestBody @Valid req: ContentsIdRequest
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsLikeModifyDto(req.contentsId, userId)
        mathConLikeModifyUseCase.save(modifyDto)
        return ResponseUtil.ok(true)
    }

    // 문제 좋아요 취소
    @DeleteMapping("/like")
    fun likeCancelContents(
        @UserId userId: UUID,
        @RequestBody @Valid req: ContentsIdRequest
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsLikeModifyDto(req.contentsId, userId)
        mathConLikeModifyUseCase.delete(modifyDto)
        return ResponseUtil.ok(true)
    }
}