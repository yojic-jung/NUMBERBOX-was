package com.kamcci.numberbox.infra.storage.adapter.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("storage", "storage-env")
class AwsS3PropertyTest {
    @Autowired
    lateinit var awsS3Property: AwsS3Property

    @Test
    fun `프로퍼티 설정 - 성공`() {
        assertThat(awsS3Property.credentials.accessKey).isNotNull()
    }
}