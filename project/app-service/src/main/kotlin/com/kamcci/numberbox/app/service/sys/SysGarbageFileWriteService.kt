package com.kamcci.numberbox.app.service.sys

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileWriteUseCase

@UseCase
class SysGarbageFileWriteService(
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort
) : SysGarbageFileWriteUseCase {

    @TXExecute
    override fun deleteById(idList: List<Long>) {
        sysGarbageFileWriteOrmPort.deleteById(idList)
    }

    @TXExecute
    override fun incrementFailCntById(id: List<Long>) {
        sysGarbageFileWriteOrmPort.incrementFailCntById(id)
    }
}