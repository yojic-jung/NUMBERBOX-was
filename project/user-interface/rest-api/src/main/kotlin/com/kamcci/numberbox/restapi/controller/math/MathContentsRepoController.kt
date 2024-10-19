package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoModifyUseCase
import com.kamcci.numberbox.restapi.dto.request.math.ContentsIdRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/math/repo/content")
class MathContentsRepoController(
    private val mathConRepoModifyUseCase: MathContentsRepoModifyUseCase,
) {
    // 저장소에 문제 저장
    @PostMapping("")
    fun putRepo(
        @UserId userId: UUID,
        @RequestBody @Valid req: ContentsIdRequest
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsRepoModifyDto(req.contentsId, userId)
        mathConRepoModifyUseCase.save(modifyDto)
        return ResponseUtil.ok(true)
    }

    // 저장소에서 삭제
    @DeleteMapping("")
    fun deleteRepo(
        @UserId userId: UUID,
        @RequestBody @Valid req: ContentsIdRequest
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsRepoModifyDto(req.contentsId, userId)
        mathConRepoModifyUseCase.delete(modifyDto)
        return ResponseUtil.ok(true)
    }

}