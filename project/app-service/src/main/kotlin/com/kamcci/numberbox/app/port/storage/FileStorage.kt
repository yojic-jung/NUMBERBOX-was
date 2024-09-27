package com.kamcci.numberbox.app.port.storage

import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import java.io.File

/**
 * 파일 저장
 */
interface FileStorage {
    // 파일 업로드
    fun upload(file: File, fileType: FileType): String

    // 파일 삭제
    fun delete(fileName: String)
}