package com.kamcci.numberbox.app.usecase.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.dto.math.MathContentsCreateDto
import com.kamcci.numberbox.app.domain.exception.BusinessValidException

/**
 * 수학문제 - 변경
 */
interface MathContentsModifyUseCase {
    /**
     * 사용자 수학문제 등록
     * @param   contentsCreateDto   수학문제 정보
     * @param   licenseCreateDto    저작권 정보
     * @return  수학문제 id
     * @throws  BusinessValidException  사용자 문제 타입이 아닌 경우
     */
    fun createUserCustomContents(
        contentsCreateDto: MathContentsCreateDto,
        licenseCreateDto: MathConLicenseCreateDto
    ): Long

    /**
     * 자체 수학문제 등록
     * @param   contentsCreateDto   수학문제 정보
     * @param   similarSrcDto       유사문제 정보
     * @return  수학문제 id
     * @throws  BusinessValidException  자체 문제 타입이 아닌 경우
     */
    fun createInHouseContents(contentsCreateDto: MathContentsCreateDto, similarSrcDto: MathConSimilarSrcCreateDto): Long


    /**
     * 변형문제 등록
     * @param   orgContentsId       원본문제 id
     * @param   contentsCreateDto   수학문제 정보
     * @return  수학문제 id
     * @throws  BusinessValidException  - 변형문제 타입이 아닌 경우
     *                                  - 원본문제 id가 존재하지 않는 경우
     */
    fun createTransContents(orgContentsId: Long, contentsCreateDto: MathContentsCreateDto): Long

    /**
     * 입시 수학문제 등록
     * @param   contentsCreateDto   수학문제 정보
     * @param   ipsiSrcCreateDto    입시 문제 정보
     * @return  수학문제 id
     * @throws  BusinessValidException  입시 문제 타입이 아닌 경우
     */
    fun createIpsiContents(contentsCreateDto: MathContentsCreateDto, ipsiSrcCreateDto: MathConIpsiSrcCreateDto): Long

}