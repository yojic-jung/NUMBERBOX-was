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
     * - 신규 파일명 경로를 만들어 저장하고 저장한 파일명과 경로를 반환
     */
    fun upload(uploadDto: FileUploadDto, fileType: FileType): FileNameVo

    /**
     * json 문자열 업로드
     * - json 문자열을 파일로 만들어 저장함
     */
    fun uploadJsonData(jsonData:String, fileType: FileType): FileNameVo

    /**
     * 파일 이름 및 저장 경로 생성
     */
    fun makeFileNameByType(fileName: String, fileType: FileType): FileNameVo
}