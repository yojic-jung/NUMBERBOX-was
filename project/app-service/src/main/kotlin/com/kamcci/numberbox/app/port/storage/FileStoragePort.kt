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

    /**
     * json 문자열 업로드
     * - json 문자열을 파일로 만들어 저장함
     */
    fun uploadJson(jsonData: String, fileName: String)

    // 파일 삭제
    fun delete(fileName: String)
}