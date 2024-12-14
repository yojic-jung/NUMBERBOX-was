package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest

@WebMvcUnitTest
class MathDocsReadControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX_URL = "/math/docs"

        // 자체제작 문제 조회
        const val IN_HOUSE_DOCS = "$PREFIX_URL/in-house"

        // 입시 문제 조회
        const val IPSI_DOCS = "$PREFIX_URL/ipsi"

        // 추가 문제 조회
        const val ADDITIONALLY_DOCS = "$PREFIX_URL/additional"

        // 문제 번호로 조회
        const val DOCS_BY_ID = "$PREFIX_URL/"

        // 나의 학습지 내역
        const val MY_DOCS = "$PREFIX_URL/my"
    }
}