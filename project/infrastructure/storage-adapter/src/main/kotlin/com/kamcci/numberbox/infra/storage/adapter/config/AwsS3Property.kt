package com.kamcci.numberbox.infra.storage.adapter.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "cloud.aws.s3",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true,
)
data class AwsS3Property(
    val credentials: Credentials,
    val bucket: String,
    val region: String
) {
    data class Credentials(
        val accessKey: String,
        val secretKey: String,
    )
}