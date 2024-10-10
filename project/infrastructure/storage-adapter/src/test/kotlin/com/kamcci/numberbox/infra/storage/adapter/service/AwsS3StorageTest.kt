package com.kamcci.numberbox.infra.storage.adapter.service

import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File

@SpringBootTest
@ActiveProfiles("storage", "storage-env")
class AwsS3StorageTest {
    @Autowired
    lateinit var awsS3UploadStorage: AwsS3StorageAdapter

    @Test
    fun `파일 객체 `() {
        // File 객체 생성
        val file = File("src/test/resources/dummy/test.txt")

        // S3에 업로드
        val fileUrl = awsS3UploadStorage.upload(file, FileType.HwpToHTML)

        // then
        assertThat(fileUrl).isNotNull()

        awsS3UploadStorage.delete("test/test.txt")
    }
}
