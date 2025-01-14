package com.kamcci.modules.logging.engine.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@EnableAspectJAutoProxy
@Configuration
@EnableConfigurationProperties(value = [LoggingTargetProperty::class])
class LoggingConfig