package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.dto.common.PageRequest
import com.kamcci.numberbox.app.domain.vo.math.MathContentsDetailVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import java.util.*

/**
 * 수학문제 조회
 */
interface MathContentsReadUseCase {
    // 문제 id로 조회
    fun findByContentsId(contentsId: Long): MathContentsVo?

    // 문제 id로 조회
    fun findByContentsId(contentsId: List<Long>, pageReq: PageRequest): List<MathContentsVo>

    // 사용자 id로 조회
    fun findByMemberId(memberId: UUID, pageReq: PageRequest): List<MathContentsDetailVo>

    // 사용자 프로필 id로 조회
    fun findByProfileId(profileId: Long, pageReq: PageRequest): List<MathContentsVo>

    // 단원으로 수학문제 조회
    fun findByUnitId(memberId: UUID, unitId: List<Int>, pageReq: PageRequest): List<MathContentsDetailVo>

    // 단원으로 수학문제 카운트
    fun countByUnitId(unitId: List<Int>): Long


    // 수학문제 id 존재 여부
    fun existById(id: Long): Boolean
}