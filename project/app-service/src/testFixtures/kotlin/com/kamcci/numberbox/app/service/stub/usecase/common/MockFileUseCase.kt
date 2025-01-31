package com.kamcci.numberbox.app.service.stub.usecase.common

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.usecase.common.FileUseCase

class MockFileUseCase : FileUseCase {
    override fun upload(uploadDto: FileUploadDto, fileType: FileType): FileNameVo {
        return FileNameVo("pptName", "pptPath")
    }

    override fun makeFileNameByType(fileName: String, fileType: FileType): FileNameVo {
        return FileNameVo("pptName", "pptPath")
    }
}