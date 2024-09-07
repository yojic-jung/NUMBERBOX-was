package com.kamcci.modules.mail.sender

import com.kamcci.modules.mail.sender.config.GoogleAccountProperty
import com.kamcci.modules.mail.sender.config.GoogleMailProperty
import com.kamcci.modules.mail.sender.config.MailSenderConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Import


@SpringBootApplication
@Import(value = [MailSenderConfig::class])
@EnableConfigurationProperties(value = [GoogleAccountProperty::class, GoogleMailProperty::class])
class MailSenderApplication