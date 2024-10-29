package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.usecase.math.MathContentsIpsiReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathFormulaKeyReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathTypeInfoReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathUnitInfoReadUseCase
import com.kamcci.numberbox.restapi.util.math.MathUnitUtil.extractUnitMap
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/math/menu")
class MathMenuController(
    private val mathUnitInfoReadUseCase: MathUnitInfoReadUseCase,
    private val mathTypeInfoReadUseCase: MathTypeInfoReadUseCase,
    private val mathContentsIpsiReadUseCase: MathContentsIpsiReadUseCase,
    private val mathFormulaKeyReadUseCase: MathFormulaKeyReadUseCase
) {
    @GetMapping("/unit")
    fun unitInfo(): ResponseEntity<ResponseData<Any>> {
        val mathUnitList = mathUnitInfoReadUseCase.findAll()
        val unitMap = extractUnitMap(mathUnitList)
        return ResponseUtil.ok(unitMap)
    }

    @GetMapping("/type")
    fun typeInfo(
        @RequestParam("unitId") unitId: String
    ): ResponseEntity<ResponseData<Any>> {
        val unitIdList = unitId.split(",").map { it.trim().toInt() }
        val mathTypeList = mathTypeInfoReadUseCase.findByUnitId(unitIdList)
        return ResponseUtil.ok(mapOf("mathTypeList" to mathTypeList))
    }

    @GetMapping("/shortCutKey")
    fun shortCutKey(): ResponseEntity<ResponseData<Any>> {
        val shortcutKeyList = mathFormulaKeyReadUseCase.findAll()
        val shortcutkeyGroupMap = shortcutKeyList.groupBy { it.classification }
        return ResponseUtil.ok(mapOf("shortCutKey" to shortcutKeyList) + shortcutkeyGroupMap)
    }

    @GetMapping("/ipsi-year")
    fun ipsiYear(): ResponseEntity<ResponseData<Any>> {
        return ResponseUtil.ok(mapOf("ipsiYear" to mathContentsIpsiReadUseCase.findAllIpsiYear()))
    }
}