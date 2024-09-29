package com.kamcci.numberbox.infra.storage.adapter.service

import com.kamcci.numberbox.app.domain.dto.port.storage.FileNameDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Profile("prod")
@Component
class AwsS3FilePathMaker : FilePathMaker {

    override fun makeFileNameByType(file: File, fileType: FileType): FileNameDto {
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
        return FileNameDto(name = fileName, path = "${rootPath}/${depth1Path}")
    }
}