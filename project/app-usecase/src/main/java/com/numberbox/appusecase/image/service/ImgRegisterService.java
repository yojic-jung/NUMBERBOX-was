package com.numberbox.appusecase.image.service;

import com.numberbox.appdomain.annotation.SingleWork;
import com.numberbox.appdomain.annotation.UseCase;
import com.numberbox.appdomain.dto.image.ImgFileInDto;
import com.numberbox.appdomain.dto.image.ImgFileMetaInfoDto;
import com.numberbox.appusecase.image.port.in.ImgRegisterUseCase;
import com.numberbox.appusecase.image.port.out.repository.TmpImgFileRepository;
import com.numberbox.appusecase.image.port.out.storage.ImgFileStorage;

import java.util.UUID;

@UseCase
public class ImgRegisterService implements ImgRegisterUseCase {

    TmpImgFileRepository tmpImgFileRepository;
    ImgFileStorage imgFileStorage;

    public ImgRegisterService(TmpImgFileRepository tmpImgFileRepository, ImgFileStorage imgFileStorage) {
        this.tmpImgFileRepository = tmpImgFileRepository;
        this.imgFileStorage = imgFileStorage;
    }

    @SingleWork
    @Override
    public void register(UUID userId, ImgFileInDto imgFileInDto) {
        ImgFileMetaInfoDto imgMetaInfoDto = imgFileStorage.upload(imgFileInDto);
        tmpImgFileRepository.save(userId, imgMetaInfoDto);
    }
}
