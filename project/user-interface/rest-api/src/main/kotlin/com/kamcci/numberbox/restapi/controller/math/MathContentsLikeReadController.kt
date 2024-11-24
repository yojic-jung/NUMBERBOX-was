package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.usecase.math.MathContentsLikeReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathContentsRepoReadUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/like-repo/content")
class MathContentsLikeReadController(
    private val mathConRepoReadUseCase: MathContentsRepoReadUseCase,
    private val mathConLikeReadUseCase: MathContentsLikeReadUseCase,
) {
    // 저장소에 문제 저장
    @GetMapping("/{contentsId}")
    fun isLikeRepoContents(
        @UserId userId: UUID,
        @PathVariable contentsId: Long
    ): ResponseEntity<ResponseData<Any>> {
        val isMyRepoContents = mathConRepoReadUseCase.existByContentsIdAndMemberId(contentsId, userId)
        val isMyLikeContents = mathConLikeReadUseCase.existByContentsIdAndMemberId(contentsId, userId)

        return ResponseUtil.ok(
            mapOf(
                "isMyRepoContents" to isMyRepoContents,
                "isMyLikeContents" to isMyLikeContents,
            )
        )
    }
}