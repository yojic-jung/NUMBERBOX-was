package com.kamcci.numberbox.app.port.storage

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto

/**
 * 파일 저장
 */
interface FileStoragePort {
    /**
     *  파일 업로드
     */
    fun upload(uploadDto: FileUploadDto)

    // 파일 삭제
    fun delete(fileName: String)
}