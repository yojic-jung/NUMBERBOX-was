package com.kamcci.numberbox.app.port.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsCreateDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType

/**
 * 수학문제 - 변경
 */
interface MathContentsModifyOrmPort {
    /**
     * 수학문제 및 저작권 저장
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsCreateDto   수학문제 정보
     * @param   licenseCreateDto    저작권 정보
     */
    fun saveWithLicense(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsCreateDto,
        licenseCreateDto: MathConLicenseCreateDto
    ): Long

    //
    /**
     * 수학문제 및 유사문제 저장
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsCreateDto   수학문제 정보
     * @param   similarSrcDto       유사문제 출처 정보
     */
    fun saveWithSimilarSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsCreateDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long

    /**
     * 수학문제 및 입시문제 출처 저장
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsCreateDto   수학문제 정보
     * @param   ipsiSrcCreateDto    입시문제 출처 정보
     */
    fun saveWithIpsiSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsCreateDto,
        ipsiSrcCreateDto: MathConIpsiSrcCreateDto
    ): Long

    /**
     * 변형 문제 저장
     * @param   orgContentsId       원본 문제 id
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsCreateDto   수학문제 정보
     */
    fun saveForTransContents(
        orgContentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsCreateDto
    ): Long

    // 변형문제 갯수 변경
    fun updateTransConCntById(id: Long, transContCnt: Int): Boolean
}