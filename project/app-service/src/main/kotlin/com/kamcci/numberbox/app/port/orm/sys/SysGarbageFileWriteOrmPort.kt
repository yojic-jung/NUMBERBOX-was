package com.kamcci.numberbox.app.port.orm.sys

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto

/**
 * 삭제 대상 유휴 파일 - 변경
 */
interface SysGarbageFileWriteOrmPort {
    // 삭제 대상 파일 저장
    fun create(fileDeleteDto: FileDeleteDto): Long
}