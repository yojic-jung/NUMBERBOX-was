package com.kamcci.numberbox.restapi.dto.request.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType

/**
 * 수학문제 생성 request
 */
data class MathContentsCreateRequest(
    // 단원 id
    val unitId: Int,
    // 유형 id
    val typeId: Int,
    // 수학문제 구분
    val contentsClassify: ContentsClassifyType,
    // 수학 문제
    val contents: String,
    // 해설
    val solution: String?,
    // 주관식 정답
    val answer: String?,
    // 객관식 정답
    val choiceAnswer: List<String>?,
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
    // 난이도
    val quesLevel: Int,
    // 공유 여부
    val shareStts: Boolean,
    // 온라인 공유 여부
    val onlineLicStts: Boolean,
    // 개인 대상 공유 여부
    val perLicStts: Boolean,
    // 기업 대상 공유 여부
    val entLicStts: Boolean,
)