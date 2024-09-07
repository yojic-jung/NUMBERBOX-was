package com.kamcci.modules.mail.sender.config

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("mail-sender-env", "mail-sender-local")
@SpringBootTest
class GoogleMailPropertyTest(
    @Autowired
    private val googleProp: GoogleMailProperty,
) {
    @Test
    fun `구글 정보 프로퍼티 호출 - 성공`() {
        Assertions.assertThat(googleProp.host).isNotNull()
        Assertions.assertThat(googleProp.port).isNotNull()
        Assertions.assertThat(googleProp.auth).isNotNull()
        Assertions.assertThat(googleProp.sslEnable).isNotNull()
        Assertions.assertThat(googleProp.sslTrust).isNotNull()
    }
}