package com.kamcci.numberbox.app.port.repository.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.vo.math.*
import java.util.*

/**
 * 수학문제 조회
 */
interface MathContentsReadOrmPort {
    // 문제 id로 조회
    fun findById(contentsId: Long): MathContentsVo?

    // 문제 id로 조회
    fun findById(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo>

    // 사용자 프로필 id로 조회
    fun findByProfileId(profileId: Long, pageReq: PageRequest): List<MathContentsVo>

    // 문제 id와 memberId로 조회
    fun findDetailByIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo?

    /**
     * 수학 문제 조회
     *
     * @param memberId          문제 제작자
     * @param svcPosbSttsType   서비스 가능 상태(null인 경우 구분 없이 전체)
     * @param pageReq           페이징 조건
     */
    fun findDetailByMemberId(
        memberId: UUID,
        svcPosbSttsType: ContentsSvcPosbSttsType?,
        pageReq: PageRequest
    ): List<MathContentsDetailVo>

    // 단원으로 수학문제 조회
    fun findDetailByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest): List<MathContentsDetailVo>

    // 자체제작 수학 문제와 유사문제 출처 정보
    fun findInHouseContentsById(contentsId: Long): MathInHouseContentsVo?

    // 입시 수학 문제
    fun findIpsiContentsById(contentsId: Long): MathIpsiContentsVo?

    // 변형문제 갯수 조회
    fun findTransContCntById(id: Long): Int?

    /**
     * 문제만 조회 - 문제, 좋아요 및 저장소 저장 여부만 조회
     *          - 저작권 및 출처 정보 제외
     *
     * @param   contentsId      문제 id 조회
     * @param   memberId        memberId에 해당하는 계정이 좋아요를 눌렀는지 저장소에 저장했는지 알기 위한 값
     */
    fun findContentsOnly(contentsId: Long, memberId: UUID): MathContentsOnlyVo?

    // 단원으로 수학문제 카운트
    fun countByUnitId(unitId: List<Int>): Long

    // 수학문제 id 존재 여부
    fun existById(id: Long): Boolean
}