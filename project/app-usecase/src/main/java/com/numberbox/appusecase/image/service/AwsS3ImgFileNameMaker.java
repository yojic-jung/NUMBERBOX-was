package com.numberbox.appusecase.image.service;

import com.numberbox.appdomain.annotation.UseCase;
import com.numberbox.appdomain.dto.image.ImgFileMetaInfoDto;
import com.numberbox.appdomain.enumuration.image.ImagePathType;
import com.numberbox.appusecase.image.port.in.ImgFileNameMaker;

import java.time.LocalDate;
import java.util.Random;

@UseCase
public class AwsS3ImgFileNameMaker implements ImgFileNameMaker {

    @Override
    public ImgFileMetaInfoDto make(ImagePathType imagePathType, String originFileName) {
        LocalDate now = LocalDate.now();
        int imgPathCode = Integer.parseInt(imagePathType.actionId + Integer.toString(now.getYear()) + now.getMonthValue());

        String imgPath = imagePathType.path + "/" + imgPathCode;

        String imgFileName = System.currentTimeMillis() + "_" + new Random().nextInt(100) + "_" + originFileName;

        // 폴더 이름 생성 규칙은 actionId-연월
        return new ImgFileMetaInfoDto(imagePathType.actionId, imgPathCode, imgPath, imgFileName);
    }
}
