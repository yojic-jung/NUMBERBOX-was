package com.kamcci.numberbox.infra.storage.adapter.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Aws Bean 등록
 */
@Configuration
@EnableConfigurationProperties(
    value = [AwsS3Property::class]
)
class AwsS3BeanConfig(
    private val awsS3Property: AwsS3Property
) {
    @Bean
    fun amazonS3(): AmazonS3 {
        val credentails = awsS3Property.credentials
        val awsCredentials = BasicAWSCredentials(credentails.accessKey, credentails.secretKey)
        val credentialsProvider = AWSStaticCredentialsProvider(awsCredentials)
        return AmazonS3ClientBuilder.standard()
            .withRegion(awsS3Property.region)
            .withCredentials(credentialsProvider).build()
    }

    @Bean
    fun amazonS3Client(amazonS3: AmazonS3): AmazonS3Client {
        return amazonS3 as AmazonS3Client
    }
}