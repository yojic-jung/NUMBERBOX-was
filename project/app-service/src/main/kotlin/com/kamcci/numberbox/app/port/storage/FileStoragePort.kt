package com.kamcci.numberbox.app.port.storage

import java.io.InputStream

/**
 * 파일 저장
 */
interface FileStoragePort {
    /**
     *  파일 업로드
     *
     *  반환맵 키 값
     *  name : 파일 이름
     *  path : 파일 경로
     */
    fun upload(fileName: String, inpStream: InputStream)

    // 파일 삭제
    fun delete(fileName: String)
}