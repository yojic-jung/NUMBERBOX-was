package com.kamcci.numberbox.app.port.orm.sys

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto

/**
 * 삭제 대상 유휴 파일 - 변경
 */
interface SysGarbageFileModifyOrmPort {
    // 삭제 대상 파일 저장
    fun create(createDto: FileDeleteCreateDto): Long

    // 삭제 대상 파일 저장
    fun create(createDtoList: List<FileDeleteCreateDto>)

    // 삭제
    fun deleteById(id: List<Long>)

    // 파일 삭제 실패 카운트 변경
    fun incrementFailCntById(id: List<Long>)
}