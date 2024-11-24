package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoWriteCase
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
@RequestMapping("/math/repo/content")
class MathContentsRepoWriteController(
    private val mathConRepoModifyUseCase: MathContentsRepoWriteCase,
) {
    // 저장소에 문제 저장
    @PostMapping("")
    fun createRepo(
        @UserId userId: UUID,
        @RequestBody @Valid req: ContentsIdRequest
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsRepoModifyDto(req.contentsId, userId)
        mathConRepoModifyUseCase.save(modifyDto)
        return ResponseUtil.ok(true)
    }

    // 저장소에서 삭제
    @DeleteMapping("/{contentsId}")
    fun deleteRepo(
        @UserId userId: UUID,
        @ContentsCheck
        @PathVariable contentsId: Long
    ): ResponseEntity<ResponseData<Any>> {
        val modifyDto = MathContentsRepoModifyDto(contentsId, userId)
        mathConRepoModifyUseCase.delete(modifyDto)
        return ResponseUtil.ok(true)
    }

}