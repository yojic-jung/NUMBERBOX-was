package com.kamcci.numberbox.app.service.sample

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo

object SysSampleData {
    fun getSysGarbageFileVo(id: Long = 1L): SysGarbageFileVo {
        return SysGarbageFileVo(
            id = id,
            type = GarbageFileType.S3,
            path = "path",
            name = "name",
            failCnt = 1,
        )
    }

    fun getSysGarbageFileVoList(size: Int = 100): List<SysGarbageFileVo> {
        val sysGarbageFileVoList: MutableList<SysGarbageFileVo> = mutableListOf()
        for (i in 1..size) {
            sysGarbageFileVoList.add(getSysGarbageFileVo(i.toLong()))
        }
        return sysGarbageFileVoList
    }
}