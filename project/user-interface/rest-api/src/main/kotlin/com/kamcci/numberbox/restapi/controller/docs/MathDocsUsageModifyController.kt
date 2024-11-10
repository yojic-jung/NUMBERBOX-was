package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsUsageModifyUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/math/docs/usage")
class MathDocsUsageModifyController(
    private val mathDocsUsageModifyUseCase: MathDocsUsageModifyUseCase
) {
    @PostMapping
    fun create(
        @UserId
        memberId: UUID,
        @RequestBody reqBody: MathDocsUsageCreateDto
    ): ResponseEntity<ResponseData<Any>> {
        val docsUsageId = mathDocsUsageModifyUseCase.create(memberId, reqBody)
        return ResponseUtil.ok(mapOf("docsUsageId" to docsUsageId))
    }

}