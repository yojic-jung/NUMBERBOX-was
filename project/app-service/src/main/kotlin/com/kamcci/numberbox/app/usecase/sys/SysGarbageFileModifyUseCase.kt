package com.kamcci.numberbox.app.usecase.sys

import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo

/**
 * 삭제 대상 유휴 파일 - 변경
 */
interface SysGarbageFileModifyUseCase {
    // 삭제
    fun delete(fileVo: SysGarbageFileVo): Boolean
}