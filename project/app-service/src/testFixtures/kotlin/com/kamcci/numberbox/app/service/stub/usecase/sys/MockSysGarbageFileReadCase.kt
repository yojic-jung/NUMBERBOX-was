package com.kamcci.numberbox.app.service.stub.usecase.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileReadCase

class MockSysGarbageFileReadCase : SysGarbageFileReadCase {
    override fun readAllByType(type: GarbageFileType, limit: Long): List<SysGarbageFileVo> {
        TODO("Not yet implemented")
    }
}