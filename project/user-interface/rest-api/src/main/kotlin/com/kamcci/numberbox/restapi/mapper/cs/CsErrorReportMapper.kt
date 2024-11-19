package com.kamcci.numberbox.restapi.mapper.cs

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.dto.cs.CsErrorReportCreateDto
import com.kamcci.numberbox.restapi.dto.request.cs.CsErrorReportCreateRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 *
 */
@Mapper(componentModel = "spring")
interface CsErrorReportMapper {
    companion object {
        @JvmStatic
        @Named("toFile")
        fun toFile(multipartFile: MultipartFile?): FileUploadDto? {
            return if (multipartFile == null || multipartFile.isEmpty) null
            else FileUploadDto(
                multipartFile.originalFilename!!,
                multipartFile.contentType,
                multipartFile.size,
                multipartFile.inputStream
            )
        }
    }

    // 첫번째 이미지
    @Mapping(source = "request.firstImgFile", target = "firstImgFile", qualifiedByName = ["toFile"])
    // 두번째 이미지
    @Mapping(source = "request.secondImgFile", target = "secondImgFile", qualifiedByName = ["toFile"])
    // 세번째 이미지
    @Mapping(source = "request.thirdImgFile", target = "thirdImgFile", qualifiedByName = ["toFile"])
    fun toDto(reportMemberId: UUID, request: CsErrorReportCreateRequest): CsErrorReportCreateDto
}