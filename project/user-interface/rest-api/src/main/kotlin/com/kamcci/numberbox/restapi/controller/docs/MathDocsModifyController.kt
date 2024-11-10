package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperModifyUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/docs")
class MathDocsModifyController(
    private val mathDocsPaperModifyUseCase: MathDocsPaperModifyUseCase
) {
    @PostMapping
    fun create(
        @UserId
        memberId: UUID,
        @RequestBody reqBody: MathDocsPaperCreateDto
    ): ResponseEntity<ResponseData<Any>> {
        return ResponseUtil.ok(mapOf("docsId" to mathDocsPaperModifyUseCase.create(memberId, reqBody)))
    }

    @PutMapping
    fun update(
        @UserId
        memberId: UUID,
        @RequestBody reqBody: MathDocsPaperUpdtDto
    ): ResponseEntity<ResponseData<String>> {
        mathDocsPaperModifyUseCase.update(memberId, reqBody)
        return ResponseUtil.ok()
    }
}