package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.*
import java.util.*

/**
 * 수학문제 조회
 */
interface MathContentsReadUseCase {
    // 수학 문제와 라이선스 정보
    fun readById(contentsId: Long): MathContentsVo?

    // 문제 id로 조회
    fun readById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo>

    // 문제 id와 제작자 id로 조회
    fun readDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo?

    /**
     * 수학 문제 조회
     *
     * @param memberId          문제 제작자
     * @param svcPosbSttsType   서비스 가능 상태(null인 경우 구분 없이 전체)
     * @param pageReq           페이징 조건
     */
    fun readDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo>

    /**
     * 수학 문제 조회
     *
     * @param memberId          문제 제작자
     * @param myMemberId        나의 id(좋아요, 저장소 정보 파악 목적)
     * @param svcPosbSttsType   서비스 가능 상태(null인 경우 구분 없이 전체)
     * @param pageReq           페이징 조건
     */
    fun readDetailByMemberId(
        memberId: UUID,
        myMemberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo>

    // 단원으로 수학문제 조회
    fun readDetailByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest): List<MathContentsDetailVo>

    // 자체제작 수학 문제
    fun readInHouseContentsById(contentsId: Long): MathInHouseContentsVo?

    // 입시 수학 문제
    fun readIpsiContentsById(contentsId: Long): MathIpsiContentsVo?

    /**
     * 문제만 조회 - 문제, 좋아요 및 저장소 저장 여부만 조회
     *          - 저작권 및 출처 정보 제외
     *
     * @param   contentsId      문제 id 조회
     * @param   memberId        memberId에 해당하는 계정이 좋아요를 눌렀는지 저장소에 저장했는지 알기 위한 값
     */
    fun readContentsOnly(contentsId: Long, memberId: UUID): MathContentsOnlyVo?

    // 단원으로 수학문제 카운트
    fun countByUnitId(unitId: List<Int>): Long

    // 수학문제 id 존재 여부
    fun existById(id: Long): Boolean
}