package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class FileMockBeanConfig {
    @Bean
    fun fileUseCase(): FileUseCase = mock()

    @Bean
    fun FileStoragePort(): FileStoragePort = object : FileStoragePort {
        override fun upload(uploadDto: FileUploadDto) {
        }

        override fun delete(fileName: String) {
        }
    }
}