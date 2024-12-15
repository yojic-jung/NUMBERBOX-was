package com.kamcci.numberbox.restapi.dummy

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo

object MathUnitFixture {
    fun getMathCategoryUnitVo(): List<MathCategoryUnitVo> {
        return listOf(
            MathCategoryUnitVo(1, "중1", "수와 연산", "소인수분해", "소인수분해"),
            MathCategoryUnitVo(2, "중1", "수와 연산", "소인수분해", "최대공약수와 최소공배수"),
            MathCategoryUnitVo(3, "중1", "수와 연산", "정수와 유리수", "정수와 유리수의 뜻"),
            MathCategoryUnitVo(4, "중1", "수와 연산", "정수와 유리수", "정수와 유리수의 대소 관계"),
        )
    }
}