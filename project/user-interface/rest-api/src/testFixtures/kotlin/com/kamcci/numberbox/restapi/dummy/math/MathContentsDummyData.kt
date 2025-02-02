package com.kamcci.numberbox.restapi.dummy.math

import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathConLicenseModifyDto
import com.kamcci.numberbox.app.service.dummy.MathContentsDummyData.getMathConSimilarSrcCreateDto
import com.kamcci.numberbox.restapi.dto.request.math.*

object MathContentsDummyData {
    // 객관식 정답 가능한 값
    val choiceAnswerValues = listOf("①", "②", "③", "④", "⑤")


    fun getMathContentsModifyRequest(choiceAnswer: List<String>?, quesLevel: Int?) = MathContentsModifyRequest(
        unitId = 21001,
        typeId = 1,
        contents = "",
        solution = "",
        answer = "",
        choiceAnswer = choiceAnswer ?: choiceAnswerValues,
        firNo = "1",
        secNo = "2",
        thrNo = "3",
        fourNo = "4",
        fifNo = "5",
        quesLevel = quesLevel ?: 1,
    )


    fun getMathConSimilarSrcCreateRequest() = MathConSimilarSrcCreateRequest(
        contents = getMathContentsModifyRequest(null, null),
        similarSrc = getMathConSimilarSrcCreateDto(),
    )


    fun getMathConIpsiSrcCreateRequest() =
        MathConIpsiSrcCreateRequest(
            contents = getMathContentsModifyRequest(null, null),
            ipsiSrc = getMathConIpsiSrcModifyDto()
        )


    fun getMathConLicenseCreateRequest() =
        MathConLicenseCreateRequest(
            contents = getMathContentsModifyRequest(null, null),
            license = getMathConLicenseModifyDto()
        )

    fun getMathConLicenseCreateRequest(choiceAnswer: List<String>, quesLevel: Int) =
        MathConLicenseCreateRequest(
            contents = getMathContentsModifyRequest(choiceAnswer, quesLevel),
            license = getMathConLicenseModifyDto()
        )

    fun getMathConLicenseUpdtRequest() =
        MathConLicenseUpdtRequest(
            contentsId = 1L,
            contents = getMathContentsModifyRequest(null, null),
            license = getMathConLicenseModifyDto()
        )

    fun getMathConTransUpdtRequest() = MathConTransUpdtRequest(
        contentsId = 1L,
        contents = getMathContentsModifyRequest(null, null),
    )

    fun getMathConTransCreateRequest() = MathConTransCreateRequest(
        orgContentsId = 1L,
        contents = getMathContentsModifyRequest(null, null),
    )


}