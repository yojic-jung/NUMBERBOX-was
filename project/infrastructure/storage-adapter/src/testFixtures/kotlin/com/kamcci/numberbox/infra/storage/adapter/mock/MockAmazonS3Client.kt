package com.kamcci.numberbox.infra.storage.adapter.mock

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult

class MockAmazonS3Client : AmazonS3Client() {
    override fun putObject(putObjectRequest: PutObjectRequest): PutObjectResult? {
        return null
    }

    override fun deleteObject(bucketName: String?, key: String) {
    }
}