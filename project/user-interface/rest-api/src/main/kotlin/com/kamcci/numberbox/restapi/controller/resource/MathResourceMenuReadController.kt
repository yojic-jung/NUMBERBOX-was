package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.numberbox.app.usecase.resource.MathResourceMenuReadUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/public/math/resource/menu")
@RestController
class MathResourceMenuReadController(
    private val mathResourceMenuReadUseCase: MathResourceMenuReadUseCase
) {

    @GetMapping
    fun readAll(): ResponseEntity<ResponseData<Any>> {
        val menuList = mathResourceMenuReadUseCase.readAll()
        return ResponseUtil.ok(mapOf("resourceMenu" to menuList))
    }
}