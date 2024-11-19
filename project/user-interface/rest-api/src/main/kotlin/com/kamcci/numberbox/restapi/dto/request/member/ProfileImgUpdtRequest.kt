package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.restapi.validation.file.ImgFileCheck
import org.springframework.web.multipart.MultipartFile

/**
 * 사용자 프로필 이미지 변경
 */
data class ProfileImgUpdtRequest(
    @field:ImgFileCheck
    val imgFile: MultipartFile
)