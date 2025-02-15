package com.kamcci.modules.mail.sender.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.reflect.full.findAnnotation

class MailSenderConfigTest {

    @Test
    fun `config 인스턴스화 가능 구조 - 성공`() {
        // config는 스프링이 내부적으로 인스턴스화 진행
        assertDoesNotThrow {
            MailSenderConfig()
        }
    }


    @Test
    fun `config 어노테이션 설정 - 성공`() {
        val annotation = MailSenderConfig::class.findAnnotation<Configuration>()

        // then - 어노테이션 부착 검증
        assertThat(annotation).isNotNull
    }

    @Test
    fun `EnableConfigurationProperties 어노테이션 설정 - 성공`() {
        val annotation =
            MailSenderConfig::class.findAnnotation<EnableConfigurationProperties>() as EnableConfigurationProperties

        // then
        // 어노테이션 부착 검증
        assertThat(annotation).isNotNull

        // property 설정 검증
        val annotValues = annotation.value
        assertThat(annotValues).contains(GoogleAccountProperty::class)
        assertThat(annotValues).contains(GoogleMailProperty::class)
    }
}