package com.kamcci.numberbox.app.service.sys

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileModifyOrmPort
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileModifyUseCase

@UseCase
class SysGarbageFileModifyService(
    private val sysGarbageFileModifyOrmPort: SysGarbageFileModifyOrmPort
) : SysGarbageFileModifyUseCase {

    @TXExecute
    override fun deleteById(idList: List<Long>) {
        sysGarbageFileModifyOrmPort.deleteById(idList)
    }

    @TXExecute
    override fun incrementFailCntById(id: List<Long>) {
        sysGarbageFileModifyOrmPort.incrementFailCntById(id)
    }
}