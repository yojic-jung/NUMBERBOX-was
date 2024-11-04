package com.kamcci.numberbox.app.service.common.file

import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.usecase.common.file.FileNameMaker
import java.time.LocalDateTime

@UseCase
class FileNameMakeService : FileNameMaker {
    companion object {
        const val FILE_NAME_LENGTH = 10
    }

    override fun makeRandomString(length: Int): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9') // 대문자, 소문자 알파벳, 숫자
        return (1..length)
            .map { chars.random() }  // chars에서 무작위로 선택
            .joinToString("")
    }

    override fun makeFileNameByType(fileName: String, fileType: FileType): FileNameVo {
        val now = LocalDateTime.now()
        val currentTime = System.currentTimeMillis()
        val randomString = makeRandomString(FILE_NAME_LENGTH)

        val dotIndex = fileName.lastIndexOf('.')
        val fileExtension = if (dotIndex != -1 && dotIndex < fileName.length - 1) {
            fileName.substring(dotIndex + 1)
        } else {
            "" // 확장자가 없을 경우 빈 문자열 반환
        }

        // 최상위 폴더 경로
        val rootPath = fileType.path
        // depth1 폴더
        val depth1Path = "${fileType.actionId}${now.year}${now.monthValue}"
        // 신규 파일 이름(파일이름간 중복 제거 목적)
        val newFileName = "${currentTime}_${randomString}.${fileExtension}"

        // 파일 경로와 이름 반환
        return FileNameVo(name = newFileName, path = "${rootPath}/${depth1Path}")
    }
}