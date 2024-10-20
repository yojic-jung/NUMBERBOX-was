package com.kamcci.numberbox.restapi.util.math

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.vo.math.MathUnitInfoVo
import com.kamcci.numberbox.restapi.dto.request.math.MathContentsSearchRequest
import com.kamcci.numberbox.restapi.dto.response.math.MathUnitGroupResponse

object MathUnitUtil {
    // 단원 그룹별 추출
    fun extractUnitList(
        mathUnitList: List<MathUnitInfoVo>,
        // MathUnitInfoVo 인풋으로 받아 속성을 반환하는 함수인자
        id: (MathUnitInfoVo) -> Int,
        parentVal: (MathUnitInfoVo) -> String,
        mainVal: (MathUnitInfoVo) -> String,
    ): List<MathUnitGroupResponse> {
        val resultList: MutableList<MathUnitGroupResponse> = mutableListOf()

        for (info in mathUnitList) {
            val key = mainVal(info)
            if (resultList.isEmpty() || resultList.last().unitName != key) {
                resultList.add(MathUnitGroupResponse(id(info), parentVal(info), key))
            }
        }

        return resultList
    }

    // 전체 단원 정보 반환
    fun extractUnitMap(mathUnitList: List<MathUnitInfoVo>): Map<String, List<MathUnitGroupResponse>> {
        // 학년 추출
        val subjectList = extractUnitList(mathUnitList, { it.id }, { it.id.toString() }, { it.subject })

        // 대단원 추출
        val firUnitList = extractUnitList(mathUnitList, { it.id }, { it.subject }, { it.firUnit })

        // 중단원 추출
        val secUnitList = extractUnitList(mathUnitList, { it.id }, { it.subject }, { it.secUnit })

        // 소단원 추출
        val thrUnitList = extractUnitList(mathUnitList, { it.id }, { it.secUnit }, { it.thrUnit })

        return mapOf(
            "subjectList" to subjectList,
            "firUnitList" to firUnitList,
            "secUnitList" to secUnitList,
            "thrUnitList" to thrUnitList,
        )
    }

    // 같은 depth에 존재하는 단원 id 추출
    fun getUnitIdList(
        unitInfoList: List<MathUnitInfoVo>,
        searchType: MathContentsSearchRequest.SearchType,
        unitId: Int
    ): List<Int> {
        val unitInfo = when {
            searchType == MathContentsSearchRequest.SearchType.ThrUnit -> return listOf(unitId)
            searchType == MathContentsSearchRequest.SearchType.SecUnit -> unitInfoList.find { it.id == unitId }
            searchType == MathContentsSearchRequest.SearchType.FirUnit -> unitInfoList.find { it.id == unitId }
            searchType == MathContentsSearchRequest.SearchType.Subject -> unitInfoList.find { it.id == unitId }
            else -> throw BusinessValidException("단원 id 검색 조건을 입력해주세요.")
        }

        return when {
            searchType == MathContentsSearchRequest.SearchType.SecUnit -> unitInfoList.filter { it.secUnit == unitInfo!!.secUnit }
                .map { it.id }

            searchType == MathContentsSearchRequest.SearchType.FirUnit -> unitInfoList.filter { it.firUnit == unitInfo!!.firUnit }
                .map { it.id }

            else -> unitInfoList.filter { it.subject == unitInfo!!.subject }.map { it.id }
        }
    }

}