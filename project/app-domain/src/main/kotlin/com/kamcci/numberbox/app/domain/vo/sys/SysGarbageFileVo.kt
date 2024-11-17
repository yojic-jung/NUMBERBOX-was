package com.kamcci.numberbox.app.domain.vo.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType

/**
 * 삭제 대상 유휴 파일
 */
data class SysGarbageFileVo(
    val id: Long,
    val type: GarbageFileType,
    val path: String,
    val name: String,
    val failCnt: Int
)