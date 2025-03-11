package com.kamcci.modules.system.construction.tx.config

import com.kamcci.modules.system.construction.mock.common.MockPlatformTransactionManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.mock.env.MockEnvironment

class SystemConstructionTxConfigTest {
    @Test
    fun `config 빈 등록 설정 - 성공`() {
        // given
        val env = MockEnvironment()
        env.setProperty(
            "system.construction.tx.class-path.transaction",
            "com.kamcci.modules.system.construction.sample.TXExecute"
        )
        val txConfig = SystemConstructionTxConfig(MockPlatformTransactionManager())

        // when & then
        assertThat(txConfig.txAnnotationPointcut()).isNotNull
        assertThat(txConfig.customTransactionAdvice()).isNotNull
        assertThat(txConfig.customTransactionAdvisor()).isNotNull

    }
}
