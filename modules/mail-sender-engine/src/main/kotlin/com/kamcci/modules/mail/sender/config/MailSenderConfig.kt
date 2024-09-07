package com.kamcci.modules.mail.sender.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    value = [GoogleAccountProperty::class, GoogleMailProperty::class]
)
class MailSenderConfig