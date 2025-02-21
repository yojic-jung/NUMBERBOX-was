package com.kamcci.numberbox.restapi.config.stub

import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.service.mock.port.storage.MockFileStoragePort
import com.kamcci.numberbox.app.service.mock.usecase.common.MockFileUseCase
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import org.springframework.context.annotation.Bean

class FileMockBeanConfig {
    @Bean
    fun fileUseCase(): FileUseCase = MockFileUseCase()

    @Bean
    fun FileStoragePort(): FileStoragePort = MockFileStoragePort()
}