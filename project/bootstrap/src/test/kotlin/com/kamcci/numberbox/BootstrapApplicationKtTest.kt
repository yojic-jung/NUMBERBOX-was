package com.kamcci.numberbox

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.BeanCreationException

@Disabled
class BootstrapApplicationKtTest {
    @Test
    fun `부트스트랩 함수 실행 - 실패`() {
        // main 함수의 실행 가능성에 대해서만 테스트
        assertThrows<BeanCreationException> {
            main(arrayOf("--spring.profiles.active=fail"))
        }
    }
}