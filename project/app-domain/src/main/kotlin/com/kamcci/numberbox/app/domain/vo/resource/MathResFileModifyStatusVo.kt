package com.kamcci.numberbox.app.domain.vo.resource

/**
 * 수학 자료(도형, 그래프 등) pdf 및 이미지 파일 수정 여부
 */
data class MathResFileModifyStatusVo(
    val isPptModified: Boolean,
    val isImgModified: Boolean,
)