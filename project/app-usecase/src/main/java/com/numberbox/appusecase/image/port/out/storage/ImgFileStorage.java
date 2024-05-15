package com.numberbox.appusecase.image.port.out.storage;

import com.numberbox.appdomain.dto.image.ImgFileInDto;
import com.numberbox.appdomain.dto.image.ImgFileMetaInfoDto;

public interface ImgFileStorage {
    ImgFileMetaInfoDto upload(ImgFileInDto imgFileInDto);
}
