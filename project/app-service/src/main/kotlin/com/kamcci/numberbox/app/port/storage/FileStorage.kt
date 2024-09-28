package com.kamcci.numberbox.app.port.storage

import com.kamcci.numberbox.app.domain.dto.port.storage.FileNameDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import java.io.File

/**
 * 파일 저장
 */
interface FileStorage {
    /**
     *  파일 업로드
     *
     *  반환맵 키 값
     *  name : 파일 이름
     *  path : 파일 경로
     */
    fun upload(file: File, fileType: FileType): FileNameDto

    // 파일 삭제
    fun delete(fileName: String)
}