//package com.kamcci.numberbox.restapi.mapper.resource
//
//import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
//import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
//import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
//import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceCreateRequest
//import com.kamcci.numberbox.restapi.dto.request.resource.MathResourceUpdateRequest
//import com.kamcci.numberbox.restapi.util.file.FileUtil
//import org.mapstruct.Mapper
//import org.mapstruct.Mapping
//import org.mapstruct.Named
//import org.springframework.web.multipart.MultipartFile
//import java.util.*
//
//@Mapper(componentModel = "spring")
//interface MathResourceMapper {
//
//    companion object {
//        @JvmStatic
//        @Named("toFile")
//        fun toFile(multipartFile: MultipartFile?): FileUploadDto? {
//            return if (multipartFile == null || multipartFile.isEmpty) null
//            else FileUploadDto(
//                multipartFile.originalFilename!!,
//                multipartFile.contentType,
//                multipartFile.size,
//                multipartFile.inputStream
//            )
//        }
//
//        @JvmStatic
//        @Named("toSlideImgList")
//        fun toSlideImgList(multipartFile: MultipartFile?): List<FileUploadDto> {
//            return if (multipartFile == null || multipartFile.isEmpty) listOf()
//            else FileUtil.toPptSlide(multipartFile)
//        }
//    }
//
//    @Mapping(source = "req.pptFile", target = "pptFile", qualifiedByName = ["toFile"])
//    @Mapping(source = "req.pptFile", target = "slideImgList", qualifiedByName = ["toSlideImgList"])
//    @Mapping(source = "req.imgFile", target = "imgFile", qualifiedByName = ["toFile"])
//    fun toDto(memberId: UUID, req: MathResourceCreateRequest): MathResourceCreateDto
//
//    @Mapping(source = "req.pptFile", target = "pptFile", qualifiedByName = ["toFile"])
//    @Mapping(source = "req.pptFile", target = "slideImgList", qualifiedByName = ["toSlideImgList"])
//    @Mapping(source = "req.imgFile", target = "imgFile", qualifiedByName = ["toFile"])
//    fun toDto(memberId: UUID, req: MathResourceUpdateRequest): MathResourceUpdateDto
//
//}