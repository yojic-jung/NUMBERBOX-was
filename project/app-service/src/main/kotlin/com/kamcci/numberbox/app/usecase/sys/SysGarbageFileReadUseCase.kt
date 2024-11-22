package com.kamcci.numberbox.app.usecase.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo

/**
 * 삭제 대상 유휴 파일 - 조회
 */
interface SysGarbageFileReadUseCase {
    /**
     * 삭제 대상 파일 조회
     */
    fun readAllByType(type: GarbageFileType, limit: Long): List<SysGarbageFileVo>
}