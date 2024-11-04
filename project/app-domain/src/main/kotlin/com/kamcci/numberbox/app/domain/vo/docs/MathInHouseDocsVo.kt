package com.kamcci.numberbox.app.domain.vo.docs

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import java.time.LocalDateTime

/**
 * 자체제작 수학문제 학습지
 */
data class MathInHouseDocsVo(
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
    val sysCreateDate: LocalDateTime
)