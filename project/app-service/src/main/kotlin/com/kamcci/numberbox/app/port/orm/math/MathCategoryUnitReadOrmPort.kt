package com.kamcci.numberbox.app.port.orm.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo

interface MathCategoryUnitReadOrmPort {
    // 단원 정보 전체 조회
    fun readAll(): List<MathCategoryUnitVo>
}