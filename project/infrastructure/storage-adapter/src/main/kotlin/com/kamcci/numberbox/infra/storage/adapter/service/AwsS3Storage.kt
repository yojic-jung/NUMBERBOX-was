package com.kamcci.numberbox.infra.storage.adapter.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.port.storage.FileStorage
import com.kamcci.numberbox.infra.storage.adapter.config.AwsS3Property
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Service
class AwsS3Storage(
    private val awsS3Property: AwsS3Property,
    private val s3Client: AmazonS3Client
) : FileStorage {
    override fun upload(file: File, fileType: FileType): String {
        // 파일 경로 설정
        val fileName = makeFileNameByType(file, fileType)

        // s3 저장 요청 객체 생성
        val putRequest = PutObjectRequest(awsS3Property.bucket, fileName, file.inputStream(), null)
            .withCannedAcl(CannedAccessControlList.PublicRead)

        // 스토리지에 저장
        s3Client.putObject(putRequest)

        // 저장한 파일 url 반환
        return s3Client.getUrl(awsS3Property.bucket, fileName).toString()
    }

    override fun delete(fileName: String) {
        s3Client.deleteObject(awsS3Property.bucket, fileName)
    }

    // 파일 경로 설정
    private fun makeFileNameByType(file: File, fileType: FileType): String {
        val now = LocalDateTime.now()
        val currentTime = System.currentTimeMillis()
        val randomValue: Int = Random().nextInt(100)

        // 최상위 폴더 경로
        val rootPath = fileType.path
        // depth1 폴더
        val depth1Path = "${fileType.actionId}${now.year}${now.month}"
        // 신규 파일 이름(파일이름간 중복 제거 목적)
        val fileName = "${currentTime}_${randomValue}_${file.name}"

        // 파일 경로와 이름 반환
        return "${rootPath}/${depth1Path}/${fileName}"
    }
}