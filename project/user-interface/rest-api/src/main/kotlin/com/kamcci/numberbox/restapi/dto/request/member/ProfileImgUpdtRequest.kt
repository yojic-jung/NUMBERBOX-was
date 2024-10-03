package com.kamcci.numberbox.restapi.dto.request.member

import org.springframework.web.multipart.MultipartFile

/**
 * 사용자 프로필 이미지 변경
 */
data class ProfileImgUpdtRequest(
    val imgFile: MultipartFile
)