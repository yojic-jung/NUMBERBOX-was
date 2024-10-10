package com.kamcci.numberbox.infra.storage.adapter.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.infra.storage.adapter.config.AwsS3Property
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class AwsS3StorageAdapter(
    private val awsS3Property: AwsS3Property,
    private val s3Client: AmazonS3Client,
    private val environment: Environment,
) : FileStoragePort {
    override fun upload(fileName: String, inpStream: InputStream) {
        // s3 저장 요청 객체 생성
        val putRequest =
            PutObjectRequest(awsS3Property.bucket, fileName, inpStream, null)
                .withCannedAcl(CannedAccessControlList.PublicRead)

        // 스토리지에 저장
        s3Client.putObject(putRequest)
    }

    override fun delete(fileName: String) {
        s3Client.deleteObject(awsS3Property.bucket, fileName)
    }
}