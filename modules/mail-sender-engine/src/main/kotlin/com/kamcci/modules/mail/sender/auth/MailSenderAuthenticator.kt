package com.kamcci.modules.mail.sender.auth

import com.kamcci.modules.mail.sender.config.GoogleAccountProperty
import org.springframework.stereotype.Component
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication

@Component
class MailSenderAuthenticator(
    private val accountProp: GoogleAccountProperty,
) : Authenticator() {
    override fun getPasswordAuthentication(): PasswordAuthentication =
        PasswordAuthentication(accountProp.email.split("@")[0], accountProp.password)

    fun requestPasswordAuthentication() = getPasswordAuthentication()
}