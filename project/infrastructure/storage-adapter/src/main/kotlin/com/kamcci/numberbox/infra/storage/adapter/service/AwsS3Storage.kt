package com.kamcci.numberbox.infra.storage.adapter.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.kamcci.numberbox.app.domain.dto.port.storage.FileNameDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.port.storage.FileStorage
import com.kamcci.numberbox.infra.storage.adapter.config.AwsS3Property
import org.springframework.stereotype.Service
import java.io.File

@Service
class AwsS3Storage(
    private val awsS3Property: AwsS3Property,
    private val s3Client: AmazonS3Client,
    private val filePathMaker: FilePathMaker
) : FileStorage {
    override fun upload(file: File, fileType: FileType): FileNameDto {
        // 파일 경로 설정
        val fileNameDto = filePathMaker.makeFileNameByType(file, fileType)

        // s3 저장 요청 객체 생성
        val putRequest =
            PutObjectRequest(awsS3Property.bucket, "${fileNameDto.path}/${fileNameDto.name}", file.inputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead)

        // 스토리지에 저장
        s3Client.putObject(putRequest)

        // 저장한 파일 url 반환
        return fileNameDto
    }

    override fun delete(fileName: String) {
        s3Client.deleteObject(awsS3Property.bucket, fileName)
    }
}