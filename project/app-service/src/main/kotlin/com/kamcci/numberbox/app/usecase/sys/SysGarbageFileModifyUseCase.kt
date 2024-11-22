package com.kamcci.numberbox.app.usecase.sys

/**
 * 삭제 대상 유휴 파일 - 변경
 */
interface SysGarbageFileModifyUseCase {
    // 삭제
    fun deleteById(idList: List<Long>)

    // 파일 삭제 실패 카운트 변경
    fun incrementFailCntById(id: List<Long>)
}