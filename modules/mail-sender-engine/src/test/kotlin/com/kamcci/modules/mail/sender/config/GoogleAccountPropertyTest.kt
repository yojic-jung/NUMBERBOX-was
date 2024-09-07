package com.kamcci.modules.mail.sender.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("mail-sender-env", "mail-sender-local")
@SpringBootTest
class GoogleAccountPropertyTest(
    @Autowired
    private val googleAccountProperty: GoogleAccountProperty
) {
    @Test
    fun `계정 정보 프로퍼티 호출 - 성공`() {
        assertThat(googleAccountProperty.email).contains("@")
        assertThat(googleAccountProperty.password).isNotNull()
    }
}