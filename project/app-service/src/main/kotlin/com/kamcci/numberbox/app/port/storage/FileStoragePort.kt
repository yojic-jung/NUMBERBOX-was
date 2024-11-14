package com.kamcci.numberbox.app.port.storage

import java.io.InputStream

/**
 * 파일 저장
 */
interface FileStoragePort {
    /**
     *  파일 업로드
     */
    fun upload(filePath: String, fileName: String, inpStream: InputStream)

    // 파일 삭제
    fun delete(fileName: String)
}