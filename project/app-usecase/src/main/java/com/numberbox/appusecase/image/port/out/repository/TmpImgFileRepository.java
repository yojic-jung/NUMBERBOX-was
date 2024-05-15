package com.numberbox.appusecase.image.port.out.repository;

import com.numberbox.appdomain.dto.image.ImgFileMetaInfoDto;

import java.util.UUID;

public interface TmpImgFileRepository {

    void save(UUID userId, ImgFileMetaInfoDto imgFileMetaInfoDto);
}
