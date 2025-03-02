package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.port.storage.FileStoragePort
import com.kamcci.numberbox.app.service.mock.port.storage.MockFileStoragePort
import com.kamcci.numberbox.app.service.mock.usecase.common.MockFileUseCase
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.restapi.util.file.FileUtil
import org.springframework.context.annotation.Bean
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

class FileMockBeanConfig {
    @Bean
    fun fileUseCase(): FileUseCase = MockFileUseCase()

    @Bean
    fun FileStoragePort(): FileStoragePort = MockFileStoragePort()

    @Bean
    fun fileUtil(): FileUtil = object : FileUtil {
        override fun toFile(multipartFile: MultipartFile): FileUploadDto {
            return FileUploadDto("", "", 0, "".byteInputStream())
        }

        override fun toPptSlide(pptInp: InputStream): List<FileUploadDto> {
            return if (pptInp.readAllBytes()
                    .contentEquals("any".toByteArray().inputStream().readAllBytes())
            ) {
                listOf(
                    FileUploadDto("", "", 0, "".toByteArray().inputStream()),
                    FileUploadDto("", "", 0, "".toByteArray().inputStream()),
                )
            } else listOf()
        }
    }
}