package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.math.MathContentsDetailVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathInHouseContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathIpsiContentsVo
import java.util.*

/**
 * 수학문제 조회
 */
interface MathContentsReadUseCase {
    // 수학 문제와 라이선스 정보
    fun findByContentsId(contentsId: Long): MathContentsVo?

    // 문제 id로 조회
    fun findByContentsId(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo>

    // 사용자 프로필 id로 조회
    fun findByProfileId(profileId: Long, pageReq: PageRequest): List<MathContentsVo>


    // 문제 id와 제작자 id로 조회
    fun findDetailByContentsIdAndMemberId(id: Long, memberId: UUID): MathContentsDetailVo?

    // 사용자 id로 조회
    fun findDetailByMemberId(memberId: UUID, pageReq: PageRequest): List<MathContentsDetailVo>

    // 단원으로 수학문제 조회
    fun findDetailByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest): List<MathContentsDetailVo>

    // 자체제작 수학 문제
    fun findInHouseContentsByContentsId(contentsId: Long): MathInHouseContentsVo?

    // 입시 수학 문제
    fun findIpsiContentsByContentsId(contentsId: Long): MathIpsiContentsVo?

    // 단원으로 수학문제 카운트
    fun countByUnitId(unitId: List<Int>): Long

    // 수학문제 id 존재 여부
    fun existById(id: Long): Boolean
}