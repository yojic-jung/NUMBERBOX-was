package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import java.util.*

/**
 * 수학문제 - 변경
 */
interface MathContentsWriteCase {
    /**
     * 사용자 수학문제 등록
     * @param   contentsModifyDto   수학문제 정보
     * @param   licenseCreateDto    저작권 정보
     * @return  수학문제 id
     */
    fun createUserCustomContents(
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long

    /**
     * 자체 수학문제 등록
     * @param   contentsModifyDto   수학문제 정보
     * @param   similarSrcDto       유사문제 정보
     * @return  수학문제 id
     */
    fun createInHouseContents(contentsModifyDto: MathContentsModifyDto, similarSrcDto: MathConSimilarSrcCreateDto): Long


    /**
     * 변형문제 등록
     * @param   contentsModifyDto   수학문제 정보
     * @return  수학문제 id
     * @throws  BusinessValidException  - 원본문제 id가 존재하지 않는 경우
     */
    fun createTransContents(orgContentsId: Long, contentsModifyDto: MathContentsModifyDto): Long

    /**
     * 입시 수학문제 등록
     * @param   contentsModifyDto   수학문제 정보
     * @param   ipsiSrcCreateDto    입시 문제 정보
     * @return  수학문제 id
     */
    fun createIpsiContents(contentsModifyDto: MathContentsModifyDto, ipsiSrcCreateDto: MathConIpsiSrcModifyDto): Long


    /**
     * 사용자 수학문제 수정
     * @param   contentsId          수학문제 id
     * @param   contentsModifyDto   수학문제 정보
     * @param   licenseCreateDto    저작권 정보
     */
    fun updateUserCustomContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Boolean

    /**
     * 자체 수학문제 수정
     * @param   contentsId          수학문제 id
     * @param   contentsModifyDto   수학문제 정보
     * @param   similarSrcDto       유사문제 정보
     */
    fun updateInHouseContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Boolean


    /**
     * 변형문제 수정
     * @param   contentsId          수학문제 id
     * @param   contentsModifyDto   수학문제 정보
     */
    fun updateTransContents(contentsId: Long, contentsModifyDto: MathContentsModifyDto): Boolean

    /**
     * 입시 수학문제 수정
     * @param   contentsId          수학문제 id
     * @param   contentsModifyDto   수학문제 정보
     * @param   ipsiSrcCreateDto    입시 문제 정보
     */
    fun updateIpsiContents(
        contentsId: Long,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Boolean

    /**
     * 수학문제 삭제 - 자기 자신의 문제만 삭제 가능
     * @param   contentsId          수학문제 id
     * @param   memberId            member id
     */
    fun delete(
        contentsId: Long,
        memberId: UUID
    )

    /**
     * 수학문제 삭제 - 사용자의 모든 문제 삭제
     * @param   memberId            member id
     */
    fun delete(memberId: UUID)
}