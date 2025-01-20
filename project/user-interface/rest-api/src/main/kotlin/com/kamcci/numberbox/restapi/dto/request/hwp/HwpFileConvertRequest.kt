package com.kamcci.numberbox.restapi.dto.request.hwp

import com.kamcci.numberbox.restapi.validation.file.HwpFileCheck
import org.springframework.web.multipart.MultipartFile

/**
 * hwp 파일로 변환하기 위한 json 형태의 문자열
 */
data class HwpFileConvertRequest(
    @field:HwpFileCheck
    val hwpFile: MultipartFile
)