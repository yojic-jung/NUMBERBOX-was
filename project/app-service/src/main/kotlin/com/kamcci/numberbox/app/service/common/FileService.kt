package com.kamcci.numberbox.app.service.common

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import java.time.LocalDateTime
import java.util.*

@UseCase
class FileService(
    private val fileStoragePort: FileStoragePort,
) : FileUseCase {
    companion object {
        const val COMPANY_NAME = "N-Soohak"
    }

    override fun upload(uploadDto: FileUploadDto, fileType: FileType): FileNameVo {
        val fileNameVo = makeFileNameByType(uploadDto.name, fileType)
        val slideImgUploadDto = FileUploadDto(
            "${fileNameVo.path}/${fileNameVo.name}",
            uploadDto.contentType,
            uploadDto.size,
            uploadDto.inputStream,
        )
        // 새 파일 업로드
        fileStoragePort.upload(slideImgUploadDto)
        return fileNameVo
    }


    override fun makeFileNameByType(fileName: String, fileType: FileType): FileNameVo {
        val now = LocalDateTime.now()
        val uuid = UUID.randomUUID()

        val dotIndex = fileName.lastIndexOf('.')
        val fileExtension = if (dotIndex != -1 && dotIndex < fileName.length - 1) {
            fileName.substring(dotIndex + 1)
        } else {
            "" // 확장자가 없을 경우 빈 문자열 반환
        }

        // 최상위 폴더 경로
        val rootPath = fileType.path
        // depth1 폴더
        val subPath = "${now.year}/${now.monthValue}"
        // 신규 파일 이름(파일 이름간 중복 제거 목적)
        val newFileName =
            "${COMPANY_NAME}_${fileType.newName}_${now.year}_${now.monthValue}_${now.dayOfMonth}_${uuid}.${fileExtension}"

        // 파일 경로와 이름 반환
        return FileNameVo(name = newFileName, path = "${rootPath}/${subPath}")
    }
}