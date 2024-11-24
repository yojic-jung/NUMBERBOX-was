package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/math/docs/usage")
class MathDocsUsageModifyController(
    private val mathDocsUsageWriteUseCase: MathDocsUsageWriteUseCase
) {
    @PostMapping
    fun create(
        @UserId
        memberId: UUID,
        @RequestBody reqBody: MathDocsUsageCreateDto
    ): ResponseEntity<ResponseData<Any>> {
        val docsUsageId = mathDocsUsageWriteUseCase.create(memberId, reqBody)
        return ResponseUtil.ok(mapOf("docsUsageId" to docsUsageId))
    }

}