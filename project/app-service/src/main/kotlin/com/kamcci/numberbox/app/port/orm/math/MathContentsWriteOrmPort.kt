package com.kamcci.numberbox.app.port.orm.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import java.util.*

/**
 * 수학문제 - 변경
 */
interface MathContentsWriteOrmPort {
    /**
     * 수학문제 및 저작권 저장
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsModifyDto   수학문제 정보
     * @param   licenseCreateDto    저작권 정보
     */
    fun saveWithLicense(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long

    /**
     * 수학문제 및 유사문제 저장
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsModifyDto   수학문제 정보
     * @param   similarSrcDto       유사문제 출처 정보
     */
    fun saveWithSimilarSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long

    /**
     * 수학문제 및 입시문제 출처 저장
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsModifyDto   수학문제 정보
     * @param   ipsiSrcCreateDto    입시문제 출처 정보
     */
    fun saveWithIpsiSrc(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long

    /**
     * 변형 문제 저장
     * @param   orgContentsId       원본 문제 id
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsModifyDto   수학문제 정보
     */
    fun saveTransContents(
        orgContentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto
    ): Long

    // 변형문제 갯수 변경
    fun updateTransConCntById(id: Long, transContCnt: Int): Boolean

    /**
     * 사용자 수학문제 수정
     * @param   contentsId          수학문제 id
     * @param   contentsModifyDto   수학문제 정보
     * @param   licenseCreateDto    저작권 정보
     * @return  수학문제 id
     */
    fun updateWithLicense(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        licenseCreateDto: MathConLicenseModifyDto
    ): Long

    /**
     * 수학문제 및 유사문제 수정
     * @param   contentsId          수학문제 id
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsModifyDto   수학문제 정보
     * @param   similarSrcDto       유사문제 출처 정보
     */
    fun updateWithSimilarSrc(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): Long

    /**
     * 수학문제 및 입시문제 출처 수정
     * @param   contentsId          수학문제 id
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsModifyDto   수학문제 정보
     * @param   ipsiSrcCreateDto    입시문제 출처 정보
     */
    fun updateWithIpsiSrc(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto,
        ipsiSrcCreateDto: MathConIpsiSrcModifyDto
    ): Long

    /**
     * 변형 문제 수정
     * @param   contentsId          수학문제 id
     * @param   svcPosbSttsType     출시 타입
     * @param   contentsModifyDto   수학문제 정보
     */
    fun updateTransContents(
        contentsId: Long,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsModifyDto: MathContentsModifyDto
    ): Long

    /**
     * 서비스 가능 상태 변경
     */
    fun updateContentsClassifyType(
        contentsId: Long,
        memberId: UUID,
        contentsClassifyType: ContentsClassifyType
    ): Boolean

    fun updateContentsClassifyType(
        memberId: UUID,
        contentsClassifyType: ContentsClassifyType
    ): Boolean
}