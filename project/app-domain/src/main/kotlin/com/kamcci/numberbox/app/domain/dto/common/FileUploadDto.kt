package com.kamcci.numberbox.app.domain.dto.common

import java.io.InputStream

/**
 * 파일 업로드 목적 dto
 */
data class FileUploadDto(
    val name: String,
    val contentType: String?,
    val size: Long,
    val inputStream: InputStream,
)