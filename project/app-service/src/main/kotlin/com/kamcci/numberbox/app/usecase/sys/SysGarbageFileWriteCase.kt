package com.kamcci.numberbox.app.usecase.sys

import com.kamcci.numberbox.app.domain.system.construction.TXExecute

/**
 * 삭제 대상 유휴 파일 - 변경
 */
interface SysGarbageFileWriteCase {
    // 삭제
    @TXExecute
    fun deleteById(idList: List<Long>): Long

    // 파일 삭제 실패 카운트 변경
    @TXExecute
    fun incrementFailCntById(id: List<Long>): Long
}