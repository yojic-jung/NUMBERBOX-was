package com.kamcci.numberbox.app.service.stub.port.storage

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.port.storage.FileStoragePort

class MockFileStoragePort : FileStoragePort {
    override fun upload(uploadDto: FileUploadDto) {
    }

    override fun delete(fileName: String) {

    }
}