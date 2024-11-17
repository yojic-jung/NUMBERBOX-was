package com.kamcci.numberbox.app.service.sys

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileModifyOrmPort
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileModifyUseCase

@UseCase
class SysGarbageFileModifyService(
    private val fileStoragePort: FileStoragePort,
    private val sysGarbageFileModifyOrmPort: SysGarbageFileModifyOrmPort
) : SysGarbageFileModifyUseCase {

    @TXExecute
    override fun delete(fileVo: SysGarbageFileVo): Boolean {
        val isDeleted = try {
            fileStoragePort.delete("${fileVo.path}/${fileVo.name}")
            true
        } catch (e: Exception) {
            // 파일 삭제 실패시 실패 카운트 증가
            sysGarbageFileModifyOrmPort.updateFailCntById(fileVo.id, fileVo.failCnt + 1)
            false
        }

        // 파일 삭제 성공시 db에서 제거
        if (isDeleted) sysGarbageFileModifyOrmPort.deleteById(fileVo.id)
        return isDeleted
    }
}