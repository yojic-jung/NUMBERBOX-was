package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeWriteUseCase
import com.kamcci.numberbox.restapi.dto.request.math.ContentsIdRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import com.kamcci.numberbox.restapi.validation.math.ContentsCheck
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/like/content")
class MathContentsLikeWriteController(
    private val mathConLikeModifyUseCase: MathContentsLikeWriteUseCase,
) {
    // 문제 좋아요
    @PostMapping("")
    fun likeContents(
        @UserId userId: UUID,
        @RequestBody @Valid req: ContentsIdRequest
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsLikeModifyDto(req.contentsId, userId)
        mathConLikeModifyUseCase.save(modifyDto)
        return ResponseUtil.ok(true)
    }

    // 문제 좋아요 취소
    @DeleteMapping("/{contentsId}")
    fun likeCancelContents(
        @UserId userId: UUID,
        @ContentsCheck
        @PathVariable contentsId: Long
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsLikeModifyDto(contentsId, userId)
        mathConLikeModifyUseCase.delete(modifyDto)
        return ResponseUtil.ok(true)
    }
}