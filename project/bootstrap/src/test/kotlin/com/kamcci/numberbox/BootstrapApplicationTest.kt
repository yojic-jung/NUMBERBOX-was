package com.kamcci.numberbox

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BootstrapApplicationTest {
    @Test
    fun `부트스트랩 실행 - 성공`() {
        main(arrayOf("--spirng.profiles=test"))
    }

    @Test
    fun `부트스트랩 클래스 생성 - 성공`() {
        assertDoesNotThrow {
            BootstrapApplication()
        }
    }
}