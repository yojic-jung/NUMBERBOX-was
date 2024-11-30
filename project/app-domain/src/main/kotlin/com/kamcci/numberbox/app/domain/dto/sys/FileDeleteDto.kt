package com.kamcci.numberbox.app.domain.dto.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType

/**
 * 삭제 대상 파일 저장 dto
 */
data class FileDeleteDto(
    var type: GarbageFileType,
    val path: String,
    val name: String,
)
