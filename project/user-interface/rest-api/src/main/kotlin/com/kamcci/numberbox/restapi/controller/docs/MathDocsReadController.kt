package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathInHouseDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/math/docs")
class MathDocsReadController(
    private val mathDocsReadUseCase: MathDocsReadUseCase
) {
    @GetMapping("/in-house")
    fun makeInHouseDocs(
        @ModelAttribute @Valid
        request: MathInHouseDocsReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val docs = mathDocsReadUseCase.makeInHouseDocs(request)
        return ResponseUtil.ok(mapOf("docs" to docs))
    }

    @GetMapping("/ipsi")
    fun makeIpsiDocs(
        @ModelAttribute @Valid
        request: MathIpsiDocsReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val docs = mathDocsReadUseCase.makeIpsiDocs(request)
        return ResponseUtil.ok(mapOf("docs" to docs))
    }

    @GetMapping("/additional")
    fun readSimilarContents(
        @ModelAttribute @Valid
        readDto: MathDocsAdditionalReadDto
    ): ResponseEntity<ResponseData<Any>> {
        val additionalContents = mathDocsReadUseCase.readAdditionalContents(readDto)
        return ResponseUtil.ok(mapOf("docs" to additionalContents))
    }

}