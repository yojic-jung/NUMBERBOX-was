package com.kamcci.numberbox.restapi.dummy.math

import com.kamcci.numberbox.app.domain.dto.math.MathConIpsiSrcModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConLicenseModifyDto
import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.app.domain.enumeration.math.*
import com.kamcci.numberbox.app.domain.vo.math.MathContentsOnlyVo
import com.kamcci.numberbox.app.domain.vo.math.MathContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathInHouseContentsVo
import com.kamcci.numberbox.app.domain.vo.math.MathIpsiContentsVo
import com.kamcci.numberbox.restapi.dto.request.math.*
import java.time.LocalDateTime
import java.util.*

object MathContentsFixture {
    // 객관식 정답 가능한 값
    val choiceAnswerValues = listOf("①", "②", "③", "④", "⑤")

    fun getMathContentsVo(): MathContentsVo {
        val now = LocalDateTime.now()
        return MathContentsVo(
            contentsId = 1L,
            memberId = UUID.randomUUID(),
            unitId = 21001,
            typeId = 1,
            contents = "contents",
            contentsImg = null,
            solution = "solution",
            solutionImg = null,
            imgPath = null,
            solutionImgPath = null,
            firNo = "1",
            secNo = "2",
            thrNo = "3",
            fourNo = "4",
            fifNo = "5",
            multiChoiceType = MultiChoiceType.Essay,
            answer = "answer",
            choiceAnswer = "choiceAnswer",
            quesLevel = 3,
            ansExistStts = true,
            svcPosbStts = ContentsSvcPosbSttsType.Release,
            contentsClassify = ContentsClassifyType.InHouse,
            orgContentsId = 1L,
            transConCnt = 3,
            sysCreateDate = now,
            sysUpdateDate = now,
            profileId = 1L,
            nickname = "nickname",
            profileImgName = "profile",
            profileImgPath = "imgPath",
            subject = "subject",
            firUnit = "21001",
            secUnit = "11",
            thrUnit = "33",
            onlineLicStts = true,
            perLicStts = true,
            perLicPrice = 10000,
            entLicStts = true,
            entLicPrice = 10000,
            shareStts = true
        )
    }

    fun getMathContentsOnlyVo(): MathContentsOnlyVo {
        val now = LocalDateTime.now()
        return MathContentsOnlyVo(
            contentsId = 1L,
            memberId = UUID.randomUUID(),
            unitId = 21001,
            typeId = 1,
            contents = "contents",
            contentsImg = null,
            solution = "solution",
            solutionImg = null,
            imgPath = null,
            solutionImgPath = null,
            firNo = "1",
            secNo = "2",
            thrNo = "3",
            fourNo = "4",
            fifNo = "5",
            multiChoiceType = MultiChoiceType.Essay,
            answer = "answer",
            choiceAnswer = "choiceAnswer",
            quesLevel = 3,
            ansExistStts = true,
            svcPosbStts = ContentsSvcPosbSttsType.Release,
            contentsClassify = ContentsClassifyType.InHouse,
            orgContentsId = 1L,
            transConCnt = 3,
            sysCreateDate = now,
            sysUpdateDate = now,
            profileId = 1L,
            nickname = "nickname",
            profileImgName = "profile",
            profileImgPath = "imgPath",
            subject = "subject",
            firUnit = "21001",
            secUnit = "11",
            thrUnit = "33",
            isMyRepoContents = true,
            isLikeContents = true,
        )
    }

    fun getMathInHouseContentsVo(): MathInHouseContentsVo {
        val now = LocalDateTime.now()
        return MathInHouseContentsVo(
            contentsId = 1L,
            memberId = UUID.randomUUID(),
            unitId = 21001,
            typeId = 1,
            contents = "contents",
            contentsImg = null,
            solution = "solution",
            solutionImg = null,
            imgPath = null,
            solutionImgPath = null,
            firNo = "1",
            secNo = "2",
            thrNo = "3",
            fourNo = "4",
            fifNo = "5",
            multiChoiceType = MultiChoiceType.Essay,
            answer = "answer",
            choiceAnswer = "choiceAnswer",
            quesLevel = 3,
            ansExistStts = true,
            svcPosbStts = ContentsSvcPosbSttsType.Release,
            contentsClassify = ContentsClassifyType.InHouse,
            orgContentsId = 1L,
            transConCnt = 3,
            sysCreateDate = now,
            sysUpdateDate = now,
            orgSrcRef = "출처",
            orgSrcNo = 1,
            orgSrcPage = 1,
            copyrightYear = "2014",
            mathTypeClassify = MathTypeClassifyType.Simple,
            similarSrcId = 1L,
            profileId = 1L,
            nickname = "nickname",
            profileImgName = "profile",
            profileImgPath = "imgPath",
            subject = "subject",
            firUnit = "21001",
            secUnit = "11",
            thrUnit = "33",
        )
    }

    fun getMathIpsiContentsVo(): MathIpsiContentsVo {
        val now = LocalDateTime.now()
        return MathIpsiContentsVo(
            contentsId = 1L,
            memberId = UUID.randomUUID(),
            unitId = 21001,
            typeId = 1,
            contents = "contents",
            contentsImg = null,
            solution = "solution",
            solutionImg = null,
            imgPath = null,
            solutionImgPath = null,
            firNo = "1",
            secNo = "2",
            thrNo = "3",
            fourNo = "4",
            fifNo = "5",
            multiChoiceType = MultiChoiceType.Essay,
            answer = "answer",
            choiceAnswer = "choiceAnswer",
            quesLevel = 3,
            ansExistStts = true,
            svcPosbStts = ContentsSvcPosbSttsType.Release,
            contentsClassify = ContentsClassifyType.InHouse,
            orgContentsId = 1L,
            transConCnt = 3,
            sysCreateDate = now,
            sysUpdateDate = now,
            profileId = 1L,
            nickname = "nickname",
            profileImgName = "profile",
            profileImgPath = "imgPath",
            subject = "subject",
            firUnit = "21001",
            secUnit = "11",
            thrUnit = "33",
            paperType = IpsiPaperType.Ka,
            oddQuesNum = 1,
            evenQuesNum = 1,
            wrongRatio = 12,
            impYear = 2012,
            impMonth = 6,
            manageIns = IpsiManageInsType.Kice,
            ipsiSrcId = 1,
        )
    }

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

    fun getMathConSimilarSrcCreateDto() = MathConSimilarSrcCreateDto(
        orgSrcRef = "N명의수학 출판",
        orgSrcNo = 100,
        orgSrcPage = 10,
        copyrightYear = "copyrightYear",
        mathTypeClassify = MathTypeClassifyType.Simple,
    )

    fun getMathConSimilarSrcCreateRequest() = MathConSimilarSrcCreateRequest(
        contents = getMathContentsModifyRequest(null, null),
        similarSrc = getMathConSimilarSrcCreateDto(),
    )

    fun getMathConIpsiSrcModifyDto() = MathConIpsiSrcModifyDto(
        manageIns = IpsiManageInsType.Kice,
        impYear = 2012,
        impMonth = 9,
        wrongRatio = 10,
        paperType = IpsiPaperType.Ka,
        oddQuesNum = 1,
        evenQuesNum = 1,
    )

    fun getMathConIpsiSrcCreateRequest() =
        MathConIpsiSrcCreateRequest(
            contents = getMathContentsModifyRequest(null, null),
            ipsiSrc = getMathConIpsiSrcModifyDto()
        )

    fun getMathConLicenseModifyDto() =
        MathConLicenseModifyDto(
            shareStts = true,
            onlineLicStts = true,
            perLicStts = true,
            entLicStts = true,
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