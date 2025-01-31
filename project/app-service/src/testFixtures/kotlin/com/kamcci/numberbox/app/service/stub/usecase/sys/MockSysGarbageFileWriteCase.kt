package com.kamcci.numberbox.app.service.stub.usecase.sys

import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileWriteCase

class MockSysGarbageFileWriteCase : SysGarbageFileWriteCase {
    override fun deleteById(idList: List<Long>): Long {
        TODO("Not yet implemented")
    }

    override fun incrementFailCntById(id: List<Long>): Long {
        TODO("Not yet implemented")
    }
}