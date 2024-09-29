package com.kamcci.numberbox.infra.storage.adapter.service

import com.kamcci.numberbox.app.domain.dto.port.storage.FileNameDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import java.io.File

interface FilePathMaker {
    fun makeFileNameByType(file: File, fileType: FileType): FileNameDto
}