package com.kamcci.numberbox.restapi.stub

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.service.stub.usecase.common.MockFileUseCase
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import org.springframework.context.annotation.Bean

class FileMockBeanConfig {
    @Bean
    fun fileUseCase(): FileUseCase = MockFileUseCase()

    @Bean
    fun FileStoragePort(): FileStoragePort = object : FileStoragePort {
        override fun upload(uploadDto: FileUploadDto) {
        }

        override fun delete(fileName: String) {
        }
    }
}