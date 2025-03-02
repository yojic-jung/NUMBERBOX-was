package com.kamcci.numberbox.infra.storage.adapter.service

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.infra.storage.adapter.config.AwsS3Property
import com.kamcci.numberbox.infra.storage.adapter.mock.MockAmazonS3Client
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AwsS3StorageTest {
    companion object {
        const val FILE_NAME = "test.txt"
        const val S3_FILE_PATH = "test/$FILE_NAME"
    }

    private lateinit var awsS3UploadStorage: AwsS3Storage

    @BeforeEach
    fun `초기화`() {
        val awsS3Property = AwsS3Property(AwsS3Property.Credentials("", ""), "", "")
        val s3Client = MockAmazonS3Client()
        awsS3UploadStorage = AwsS3Storage(awsS3Property, s3Client)
    }

    @AfterEach
    fun `테스트 데이터 후처리`() {
        awsS3UploadStorage.delete(S3_FILE_PATH)
    }

    @Test
    fun `파일 객체 업로드 - 성공`() {
        val fileByte = "".toByteArray()
        val contentType = "text/plain"

        // S3에 업로드
        val fileUploadDto = FileUploadDto(FILE_NAME, contentType, fileByte.size.toLong(), fileByte.inputStream())
        val fileUrl = awsS3UploadStorage.upload(fileUploadDto)

        // then
        assertThat(fileUrl).isNotNull()
    }

    @Test
    fun `파일 객체(content-type=null) 업로드 - 성공`() {
        // File 객체 생성
        val fileByte = "".toByteArray()
        val contentType = null

        // S3에 업로드
        val fileUploadDto = FileUploadDto(FILE_NAME, contentType, fileByte.size.toLong(), fileByte.inputStream())
        val fileUrl = awsS3UploadStorage.upload(fileUploadDto)

        // then
        assertThat(fileUrl).isNotNull()
    }

    @Test
    fun `파일 객체(content-type는 empty) 업로드 - 성공`() {
        // File 객체 생성
        val fileByte = "".toByteArray()
        val contentType = ""

        // S3에 업로드
        val fileUploadDto = FileUploadDto(FILE_NAME, contentType, fileByte.size.toLong(), fileByte.inputStream())
        val fileUrl = awsS3UploadStorage.upload(fileUploadDto)

        // then
        assertThat(fileUrl).isNotNull()
    }
}
