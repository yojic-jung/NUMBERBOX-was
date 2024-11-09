package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPapaerCreateDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperModifyUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/math/docs")
class MathDocsModifyController(
    private val mathDocsPaperModifyUseCase: MathDocsPaperModifyUseCase
) {
    @PostMapping
    fun create(
        @UserId
        memberId: UUID,
        @RequestBody reqBody: MathDocsPapaerCreateDto
    ): ResponseEntity<ResponseData<Any>> {
        return ResponseUtil.ok(mapOf("docsId" to mathDocsPaperModifyUseCase.create(memberId, reqBody)))
    }
}