package com.kamcci.numberbox.infra.storage.adapter.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AwsS3BeanConfigTest {
    // 테스트 대상
    private val awsS3BeanConfig = AwsS3BeanConfig(AwsS3Property(AwsS3Property.Credentials("", ""), "", ""))

    @Test
    fun `beanConfig 설정 - 성공`() {
        // when
        val amazonS3 = awsS3BeanConfig.amazonS3()
        val s3Client = awsS3BeanConfig.amazonS3Client(amazonS3)

        // then
        assertThat(amazonS3).isNotNull
        assertThat(s3Client).isNotNull
    }

}