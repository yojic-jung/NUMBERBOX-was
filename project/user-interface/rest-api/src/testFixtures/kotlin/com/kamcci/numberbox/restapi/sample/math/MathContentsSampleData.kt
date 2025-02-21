package com.kamcci.numberbox.restapi.sample.math

import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConLicenseModifyDto
import com.kamcci.numberbox.app.service.sample.MathContentsSampleData.getMathConSimilarSrcCreateDto
import com.kamcci.numberbox.restapi.dto.request.math.*

object MathContentsSampleData {
    // 객관식 정답 가능한 값
    val choiceAnswerValues = listOf("①", "②", "③", "④", "⑤")


    fun getMathContentsModifyRequest(choiceAnswer: List<String> = choiceAnswerValues, quesLevel: Int = 1) =
        MathContentsModifyRequest(
            unitId = 21001,
            typeId = 1,
            contents = "",
            solution = "",
            answer = "",
            choiceAnswer = choiceAnswer,
            firNo = "1",
            secNo = "2",
            thrNo = "3",
            fourNo = "4",
            fifNo = "5",
            quesLevel = quesLevel,
        )


    fun getMathConSimilarSrcCreateRequest() = MathConSimilarSrcCreateRequest(
        contents = getMathContentsModifyRequest(),
        similarSrc = getMathConSimilarSrcCreateDto(),
    )


    fun getMathConIpsiSrcCreateRequest() =
        MathConIpsiSrcCreateRequest(
            contents = getMathContentsModifyRequest(),
            ipsiSrc = getMathConIpsiSrcModifyDto()
        )


    fun getMathConLicenseCreateRequest() =
        MathConLicenseCreateRequest(
            contents = getMathContentsModifyRequest(),
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
            contents = getMathContentsModifyRequest(),
            license = getMathConLicenseModifyDto()
        )

    fun getMathConTransUpdtRequest() = MathConTransUpdtRequest(
        contentsId = 1L,
        contents = getMathContentsModifyRequest(),
    )

    fun getMathConTransCreateRequest() = MathConTransCreateRequest(
        orgContentsId = 1L,
        contents = getMathContentsModifyRequest(),
    )


}