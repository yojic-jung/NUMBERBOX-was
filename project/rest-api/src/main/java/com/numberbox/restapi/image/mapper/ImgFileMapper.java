package com.numberbox.restapi.image.mapper;

import com.numberbox.appdomain.dto.image.ImgFileInDto;
import com.numberbox.restapi.image.dto.request.ImgFileRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface ImgFileMapper {

    ImgFileMapper mapper = Mappers.getMapper(ImgFileMapper.class);

    @Mapping(target = "originFileName", source = "multipartFile.originalFilename")
    @Mapping(target = "inputStream", source = "multipartFile.inputStream")
    ImgFileInDto fromRequestToDto(ImgFileRequest request) throws IOException;
}
