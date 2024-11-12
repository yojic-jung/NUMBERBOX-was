package com.kamcci.numberbox.app.domain.vo.resource

import java.time.LocalDateTime

/**
 * 수학 자료(도형, 그래프 등) pdf
 */
data class MathResourceVo(
    // math_resource
    var id: Long,
    var title: String,
    var imgPath: String,
    var imgName: String,
    var pptPath: String,
    var pptName: String,
    var pptPageCnt: Int,
    var downCnt: Int,
    var sysCreateDate: LocalDateTime,
    var sysUpdateDate: LocalDateTime,
)