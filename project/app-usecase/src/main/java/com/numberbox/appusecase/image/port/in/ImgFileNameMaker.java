package com.numberbox.appusecase.image.port.in;

import com.numberbox.appdomain.dto.image.ImgFileMetaInfoDto;
import com.numberbox.appdomain.enumuration.image.ImagePathType;

public interface ImgFileNameMaker {
    ImgFileMetaInfoDto make(ImagePathType imagePathType, String originFileName);
}
