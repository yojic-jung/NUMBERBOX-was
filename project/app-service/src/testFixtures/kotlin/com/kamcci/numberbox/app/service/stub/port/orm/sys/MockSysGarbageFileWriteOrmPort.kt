package com.kamcci.numberbox.app.service.stub.port.orm.sys

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort

class MockSysGarbageFileWriteOrmPort : SysGarbageFileWriteOrmPort {
    override fun create(fileDeleteDto: FileDeleteDto): Long {
        return 1L
    }
}