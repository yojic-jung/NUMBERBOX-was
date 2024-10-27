package com.kamcci.numberbox.app.domain.vo.math

/**
 * 좋아요 및 저장소 저장 여부
 */
data class MathConLikeRepoVo(
    // 문제 id
    val contentsId: Long,
    // 좋아요 여부
    val isLike: Boolean,
    // 저장소 저장 여부
    val isModify: Boolean
)