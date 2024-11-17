package com.kamcci.numberbox.app.domain.vo.resource

/**
 * 수학 자료(도형, 그래프 등) pdf - 파일 정보
 */
data class MathResourceFileVo(
    var id: Long,
    var imgPath: String,
    var imgName: String,
    var pptPath: String,
    var pptName: String,
    val imgList: List<MathResourceImgVo>,
)