package com.kamcci.numberbox.app.service.dummy

import com.kamcci.numberbox.app.domain.dto.math.*
import com.kamcci.numberbox.app.domain.enumeration.math.IpsiManageInsType
import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType
import com.kamcci.numberbox.app.domain.enumeration.math.MathTypeClassifyType
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

}