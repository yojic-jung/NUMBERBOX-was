package com.kamcci.numberbox.app.domain.vo.docs

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import java.time.LocalDateTime

/**
 * 학습지용 수학문제
 * - 자체제작 문제는 문제 정보만
 * - 입시문제는 입시 출처 정보 포함
 */
data class MathAllTypeDocsVo(
    // 수학문제 id
    val contentsId: Long,
    // 단원 id
    val unitId: Int,
    // 유형 id
    val typeId: Int,
    // 수학 문제 내용
    val contents: String,
    // 수학문제 이미지
    val contentsImg: String?,
    // 수학문제 이미지 경로
    val imgPath: String?,
    // 해설
    val solution: String?,
    // 해설 이미지
    val solutionImg: String?,
    // 해설 이미지 경로
    val solutionImgPath: String?,
    // 객관식 1번
    val firNo: String?,
    // 객관식 2번
    val secNo: String?,
    // 객관식 3번
    val thrNo: String?,
    // 객관식 4번
    val fourNo: String?,
    // 객관식 5번
    val fifNo: String?,
    // 객관식/주관식 여부
    val multiChoiceType: MultiChoiceType,
    // 정답
    val answer: String?,
    // 객관식 정답
    val choiceAnswer: String?,
    // 난이도
    val quesLevel: Int,
    // 정답 존재 여부
    val ansExistStts: Boolean,
    // 문제 분류
    val contentsClassify: ContentsClassifyType,
    // 학년
    val subject: String,
    // 대단원
    val firUnit: String,
    // 중단원
    val secUnit: String,
    // 소단원
    val thrUnit: String,
    // 문제 유형
    val quesType: String,
    // 문제 생성 시간
    val sysCreateDate: LocalDateTime,
    /**
     * 입시 정보
     */
    // 출제 년도
    val impYear: Int?,
    // 출제 월
    val impMonth: Int?,
    // 홀수형 번호
    val oddQuesNum: Int?,
    // 오답률
    val wrongRatio: Int?,
    // 가/나형 구분
    val paperType: IpsiPaperType?
)