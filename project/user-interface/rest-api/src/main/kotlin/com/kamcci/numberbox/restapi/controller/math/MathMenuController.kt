package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.usecase.math.MathCategoryTypeReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathCategoryUnitReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathContentsIpsiReadUseCase
import com.kamcci.numberbox.app.usecase.math.MathFormulaKeyReadUseCase
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
    private val mathCategoryUnitReadUseCase: MathCategoryUnitReadUseCase,
    private val mathCategoryTypeReadUseCase: MathCategoryTypeReadUseCase,
    private val mathContentsIpsiReadUseCase: MathContentsIpsiReadUseCase,
    private val mathFormulaKeyReadUseCase: MathFormulaKeyReadUseCase
) {
    @GetMapping("/unit")
    fun readUnitCategory(): ResponseEntity<ResponseData<Any>> {
        val mathUnitList = mathCategoryUnitReadUseCase.readAll()
        val unitMap = extractUnitMap(mathUnitList)
        return ResponseUtil.ok(unitMap)
    }

    @GetMapping("/type")
    fun readTypeCategory(
        @RequestParam("unitId") unitId: String
    ): ResponseEntity<ResponseData<Any>> {
        val unitIdList = unitId.split(",").map { it.trim().toInt() }
        val mathTypeList = mathCategoryTypeReadUseCase.readByUnitId(unitIdList)
        return ResponseUtil.ok(mapOf("mathTypeList" to mathTypeList))
    }

    @GetMapping("/shortCutKey")
    fun readShortCutKey(): ResponseEntity<ResponseData<Any>> {
        val shortcutKeyList = mathFormulaKeyReadUseCase.readAll()
        val shortcutkeyGroupMap = shortcutKeyList.groupBy { it.classification }
        return ResponseUtil.ok(mapOf("shortCutKey" to shortcutKeyList) + shortcutkeyGroupMap)
    }

    @GetMapping("/ipsi-year")
    fun readIpsiYear(): ResponseEntity<ResponseData<Any>> {
        return ResponseUtil.ok(mapOf("ipsiYear" to mathContentsIpsiReadUseCase.readAllIpsiYear()))
    }
}