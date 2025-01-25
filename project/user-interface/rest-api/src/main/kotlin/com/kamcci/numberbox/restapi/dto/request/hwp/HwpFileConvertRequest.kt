package com.kamcci.numberbox.restapi.dto.request.hwp

import com.kamcci.numberbox.restapi.validation.file.HwpFileCheck
import org.springframework.web.multipart.MultipartFile

/**
 * html 파일로 변환하기 위한 hwp 파일
 */
data class HwpFileConvertRequest(
    @field:HwpFileCheck
    val hwpFile: MultipartFile
)