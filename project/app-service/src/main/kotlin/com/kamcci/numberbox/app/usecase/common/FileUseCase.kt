package com.kamcci.numberbox.app.usecase.common

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo

/**
 * 파일 관련 usecase
 */
interface FileUseCase {

    /**
     * 파일 업로드
     */
    fun upload(uploadDto: FileUploadDto, fileType: FileType): FileNameVo

    /**
     * 파일 이름 및 저장 경로 생성
     */
    fun makeFileNameByType(fileName: String, fileType: FileType): FileNameVo
}