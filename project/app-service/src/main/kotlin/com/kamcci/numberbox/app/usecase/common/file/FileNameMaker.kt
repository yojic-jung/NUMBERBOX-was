package com.kamcci.numberbox.app.usecase.common.file

import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo

interface FileNameMaker {
    /**
     * 임의 문자열 생성
     */
    fun makeRandomString(length: Int): String

    /**
     * 파일 이름 및 저장 경로 생성
     */
    fun makeFileNameByType(fileName: String, fileType: FileType): FileNameVo
}