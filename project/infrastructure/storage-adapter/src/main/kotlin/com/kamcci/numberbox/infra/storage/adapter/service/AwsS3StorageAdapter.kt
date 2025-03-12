package com.kamcci.numberbox.infra.storage.adapter.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.infra.storage.adapter.config.AwsS3Property
import org.springframework.stereotype.Service


@Service
class AwsS3StorageAdapter(
    private val awsS3Property: AwsS3Property,
    private val s3Client: AmazonS3Client,
) : FileStoragePort {
    override fun upload(uploadDto: FileUploadDto) {
        val metadata = ObjectMetadata()
        metadata.contentLength = uploadDto.size
        if (!uploadDto.contentType.isNullOrEmpty()) metadata.contentType = uploadDto.contentType

        // s3 저장 요청 객체 생성
        uploadDto.inputStream.use {
            val putRequest =
                PutObjectRequest(awsS3Property.bucket, uploadDto.name, it, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            // 스토리지에 저장
            s3Client.putObject(putRequest)
        }
    }

    override fun delete(fileName: String) {
        s3Client.deleteObject(awsS3Property.bucket, fileName)
    }
}