package com.kamcci.numberbox.app.domain.vo.resource

/**
 * 수학 자료(도형, 그래프 pdf) 카테고리
 */
data class MathResourceMenuVo(
    var id: Long,
    // 대분류 id
    var mainCateId: Int,
    // 대분류명
    var mainCateName: String,
    // 중분류 id
    var midCateId: Int,
    // 중분류명
    var midCateName: String,
    // 정렬 순서
    var alignOrder: Int,
)