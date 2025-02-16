package com.kamcci.numberbox.infra.storage.adapter.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.infra.storage.adapter.config.AwsS3Property
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class AwsS3StorageTest {
    private lateinit var awsS3UploadStorage: AwsS3Storage

    @BeforeEach
    fun `초기화`() {
        val awsS3Property = AwsS3Property(AwsS3Property.Credentials("", ""), "", "")
        val s3Client = MockAmazonS3Client()
        awsS3UploadStorage = AwsS3Storage(awsS3Property, s3Client)
    }

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

class MockAmazonS3Client : AmazonS3Client() {
    override fun putObject(putObjectRequest: PutObjectRequest): PutObjectResult? {
        return null
    }

    override fun deleteObject(bucketName: String?, key: String) {
    }
}