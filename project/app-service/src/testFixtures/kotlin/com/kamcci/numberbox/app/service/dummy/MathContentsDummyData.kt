package com.kamcci.numberbox.app.service.dummy

import com.kamcci.numberbox.app.domain.dto.math.*
import com.kamcci.numberbox.app.domain.enumeration.math.*
import com.kamcci.numberbox.app.domain.vo.math.*
import java.time.LocalDateTime
import java.util.*

object MathContentsDummyData {
    fun getMathContentsRepoModifyDto() = MathContentsRepoModifyDto(
        contentsId = 1L,
        memberId = UUID.randomUUID()
    )

    fun getMathContentsModifyDto() = MathContentsModifyDto(
        UUID.randomUUID(),
        1,
        1,
        "",
        "",
        "",
        listOf(""),
        "",
        "",
        "",
        "",
        "",
        1
    )

    fun getMathConSimilarSrcCreateDto() = MathConSimilarSrcCreateDto(
        orgSrcRef = "N명의수학 출판",
        orgSrcNo = 100,
        orgSrcPage = 10,
        copyrightYear = "copyrightYear",
        mathTypeClassify = MathTypeClassifyType.Simple,
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

    fun getMathConLicenseModifyDto() =
        MathConLicenseModifyDto(
            shareStts = true,
            onlineLicStts = true,
            perLicStts = true,
            entLicStts = true,
        )

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

    fun getMathContentsVo(id: Long = 1L): MathContentsVo {
        val now = LocalDateTime.now()
        return MathContentsVo(
            contentsId = id,
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

    fun getMathContentsVoList(size: Int = 100): List<MathContentsVo> {
        val contentsList: MutableList<MathContentsVo> = mutableListOf()
        for (i in 1..size) {
            contentsList.add(getMathContentsVo())
        }
        return contentsList
    }

    fun getMathContentsDetailVo(id: Long = 1L): MathContentsDetailVo {
        val now = LocalDateTime.now()
        return MathContentsDetailVo(
            contentsId = id,
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
            onlineLicStts = true,
            perLicStts = true,
            perLicPrice = 10000,
            entLicStts = true,
            entLicPrice = 10000,
            shareStts = true,
            profileId = 1L,
            nickname = "nickname",
            profileImgName = "profile",
            profileImgPath = "imgPath",
            subject = "subject",
            firUnit = "21001",
            secUnit = "11",
            thrUnit = "33",
            isMyRepoContents = true,
            isLikeContents = true
        )
    }

    fun getMathContentsDetailVoList(size: Int = 100): List<MathContentsDetailVo> {
        val detailList: MutableList<MathContentsDetailVo> = mutableListOf()
        for (i in 1..size) {
            detailList.add(getMathContentsDetailVo())
        }
        return detailList
    }


    fun getMathCategoryUnitVo(): List<MathCategoryUnitVo> {
        return listOf(
            MathCategoryUnitVo(21001, "중1", "수와 연산", "소인수분해", "소인수분해"),
            MathCategoryUnitVo(21002, "중1", "수와 연산", "소인수분해", "최대공약수와 최소공배수"),
            MathCategoryUnitVo(21003, "중1", "수와 연산", "정수와 유리수", "정수와 유리수의 뜻"),
            MathCategoryUnitVo(21004, "중1", "수와 연산", "정수와 유리수", "정수와 유리수의 대소 관계"),
        )
    }

    fun getMathFormulaKeyVo(id: Int, classification: FormulaClassificationType) =
        MathFormulaKeyVo(
            id = id,
            formulOrder = 1,
            formulName = "asd",
            formulUi = "sddf",
            shortcutKey = "dsf",
            latexGrammer = "sdf",
            nbGrammer = "sdf",
            guide = "sdf",
            shortcutKeycode = "d",
            texGrammer = "tex",
            lineChange = 1,
            classification = classification,
        )

    fun getMathFormulaKeyVoList(): List<MathFormulaKeyVo> {
        return listOf(
            getMathFormulaKeyVo(1, FormulaClassificationType.Main),
            getMathFormulaKeyVo(2, FormulaClassificationType.Main),
            getMathFormulaKeyVo(3, FormulaClassificationType.Main),
            getMathFormulaKeyVo(4, FormulaClassificationType.High1),
            getMathFormulaKeyVo(5, FormulaClassificationType.High1),
            getMathFormulaKeyVo(6, FormulaClassificationType.High1),
        )
    }

    fun getMathCategoryTypeVo(unitId: Int = 21001) = MathCategoryTypeVo(
        unitId = 21001,
        typeId = 1,
        quesType = "dsajlkf",
        typeOrder = 1
    )

    fun getMathCategoryTypeVoList(size: Int = 100): List<MathCategoryTypeVo> {
        val categoryVoList: MutableList<MathCategoryTypeVo> = mutableListOf()
        for (i in 1..size) {
            categoryVoList.add(getMathCategoryTypeVo(21001 + i))
        }
        return categoryVoList
    }
}