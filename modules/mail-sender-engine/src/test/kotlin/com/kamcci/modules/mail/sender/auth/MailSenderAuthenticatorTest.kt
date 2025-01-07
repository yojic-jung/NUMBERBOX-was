package com.kamcci.modules.mail.sender.auth

import com.kamcci.modules.mail.sender.config.GoogleAccountProperty
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MailSenderAuthenticatorTest {
    @Test
    fun getPasswordAuthentication() {
        // given
        val userName = "userName"
        val password = "password"
        val googleAccountProp = GoogleAccountProperty(userName, password)
        val mailSenderAuthenticator = MailSenderAuthenticator(googleAccountProp)

        // when
        val passwordAuthentication =
            mailSenderAuthenticator.requestPasswordAuthentication()

        // then
        assertThat(passwordAuthentication.userName).isEqualTo(userName)
        assertThat(passwordAuthentication.password).isEqualTo(password)
    }
}