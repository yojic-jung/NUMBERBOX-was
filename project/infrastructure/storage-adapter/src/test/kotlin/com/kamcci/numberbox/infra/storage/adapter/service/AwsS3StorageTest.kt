package com.kamcci.numberbox.infra.storage.adapter.service

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File

@SpringBootTest
@ActiveProfiles("storage", "storage-env")
class AwsS3StorageTest {
    @Autowired
    lateinit var awsS3UploadStorage: AwsS3Storage

    @AfterEach
    fun `테스트 데이터 후처리`() {
        awsS3UploadStorage.delete("test/test.txt")
    }

    @Test
    fun `파일 객체 업로드 - 성공`() {
        // File 객체 생성
        val name = "src/test/resources/dummy/test.txt"
        val file = File(name)

        // S3에 업로드
        val fileUploadDto = FileUploadDto(name, "text/plain", file.length(), file.inputStream())
        val fileUrl = awsS3UploadStorage.upload(fileUploadDto)

        // then
        assertThat(fileUrl).isNotNull()

    }

    @Test
    fun `파일 객체(content-type=null) 업로드 - 성공`() {
        // File 객체 생성
        val name = "src/test/resources/dummy/test.txt"
        val file = File(name)

        // S3에 업로드
        val fileUploadDto = FileUploadDto(name, null, file.length(), file.inputStream())
        val fileUrl = awsS3UploadStorage.upload(fileUploadDto)

        // then
        assertThat(fileUrl).isNotNull()
    }

    @Test
    fun `파일 객체(content-type는 empty) 업로드 - 성공`() {
        // File 객체 생성
        val name = "src/test/resources/dummy/test.txt"
        val file = File(name)

        // S3에 업로드
        val fileUploadDto = FileUploadDto(name, "", file.length(), file.inputStream())
        val fileUrl = awsS3UploadStorage.upload(fileUploadDto)

        // then
        assertThat(fileUrl).isNotNull()
    }
}
