package com.kamcci.numberbox.hwp.client.adapter.config

import org.junit.jupiter.api.Test

class HwpSocketClientConfigTest {
    @Test
    fun `config 상속 가능 구조 - 성공`() {
        MockHwpSocketClientConfig()
    }
}

class MockHwpSocketClientConfig : HwpSocketClientConfig()