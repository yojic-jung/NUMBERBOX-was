package com.kamcci.numberbox.app.usecase.common.file

import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo

interface FileNameMaker {
    fun makeFileNameByType(fileName: String, fileType: FileType): FileNameVo
}