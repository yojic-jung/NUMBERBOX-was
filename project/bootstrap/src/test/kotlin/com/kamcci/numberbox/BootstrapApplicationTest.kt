package com.kamcci.numberbox

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.boot.SpringApplication
import org.springframework.context.support.GenericXmlApplicationContext

class BootstrapApplicationTest {
    @Test
    fun `부트스트랩 실행 여부 검증 - 성공`() {
        // given
        mockkStatic(SpringApplication::class)
        every { SpringApplication.run(any<Class<*>>(), *anyVararg()) } returns GenericXmlApplicationContext()

        // when
        main(arrayOf("--spirng.profiles=test"))

        // then
        verify {
            SpringApplication.run(any<Class<*>>(), *anyVararg())
        }
        cleanUp()
    }

    @Test
    fun `부트스트랩 인스턴스화 가능 검증 - 성공`() {
        assertDoesNotThrow {
            BootstrapApplication()
        }
    }

    private fun cleanUp() {
        // mockkStatic을 해제하여 static 메서드 모킹을 종료
        unmockkStatic(SpringApplication::class)
    }
}