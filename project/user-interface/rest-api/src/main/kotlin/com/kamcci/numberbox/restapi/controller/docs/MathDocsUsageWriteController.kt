package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageWriteCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
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
class MathDocsUsageWriteController(
    private val mathDocsUsageWriteCase: MathDocsUsageWriteCase
) {
    @PostMapping
    fun create(
        @UserId
        memberId: UUID,
        @Valid
        @RequestBody reqBody: MathDocsUsageCreateDto
    ): ResponseEntity<ResponseData<Any>> {
        val docsUsageId = mathDocsUsageWriteCase.create(memberId, reqBody)
        return ResponseUtil.ok(mapOf("docsUsageId" to docsUsageId))
    }

}