package com.kamcci.numberbox.infra.orm.jpa.adapter.sample.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.sample.member.MembersSampleData
import java.time.LocalDateTime

object MathContentsSampleData {
    fun getSaveEntity(): MathContentsEntity {
        val now = LocalDateTime.now()

        return MathContentsEntity().apply {
            unitId = 22001
            typeId = 1
            memberId = MembersSampleData.getMemberId1()
            contents = ""
            contentsImg = ""
            imgPath = ""
            solution = ""
            solutionImg = ""
            solutionImgPath = ""
            firNo = ""
            secNo = ""
            thrNo = ""
            fourNo = ""
            fifNo = ""
            multiChoiceType = MultiChoiceType.Essay
            answer = ""
            choiceAnswer = "1"
            orgSrcRef = ""
            orgSrcNo = 1
            quesLevel = 1
            ansExistStts = true
            svcPosbStts = ContentsSvcPosbSttsType.Release
            contentsClassify = ContentsClassifyType.InHouse
            orgContentsId = 0
            transConCnt = 0
            sysCreateDate = now
            sysUpdateDate = now
        }
    }

    // 주관식, 객관식 정답 존재
    fun getMathContentsModifyDto(answer: String?, choiceAnswer: List<String>?): MathContentsModifyDto {
        return MathContentsModifyDto(
            memberId = MembersSampleData.getMemberId1(),
            unitId = 21001,
            typeId = 1,
            contents = "asddfasdf",
            solution = "asdfa",
            answer = answer,
            choiceAnswer = choiceAnswer,
            firNo = "1",
            secNo = "2",
            thrNo = "3",
            fourNo = "4",
            fifNo = "5",
            quesLevel = 1
        )
    }

}