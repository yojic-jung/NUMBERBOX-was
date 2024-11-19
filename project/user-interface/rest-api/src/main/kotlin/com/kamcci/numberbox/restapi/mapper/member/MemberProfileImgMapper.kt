package com.kamcci.numberbox.restapi.mapper.member

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.restapi.dto.request.member.ProfileImgUpdtRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Mapper(componentModel = "spring")
interface MemberProfileImgMapper {
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

    @Mapping(source = "request.imgFile", target = "imgFile", qualifiedByName = ["toFile"])
    fun toDto(memberId: UUID, request: ProfileImgUpdtRequest): MemberProfileImgUpdtDto
}