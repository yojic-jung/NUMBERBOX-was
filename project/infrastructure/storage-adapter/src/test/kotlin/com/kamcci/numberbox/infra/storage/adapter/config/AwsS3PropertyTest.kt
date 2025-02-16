package com.kamcci.numberbox.infra.storage.adapter.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.ConfigurationProperties
import kotlin.reflect.full.findAnnotation

class AwsS3PropertyTest {
    @Test
    fun `프로퍼티 어노테이션 부착 검증 - 성공`() {
        val annotation = AwsS3Property::class.findAnnotation<ConfigurationProperties>()

        // then
        assertThat(annotation).isNotNull()
    }
}