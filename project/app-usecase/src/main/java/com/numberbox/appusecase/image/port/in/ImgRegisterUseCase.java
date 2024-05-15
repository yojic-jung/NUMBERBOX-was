package com.numberbox.appusecase.image.port.in;

import com.numberbox.appdomain.dto.image.ImgFileInDto;

import java.util.UUID;

/**
 * Def. 이미지 파일 업로드
 */
public interface ImgRegisterUseCase {
    void register(UUID userId, ImgFileInDto imgFileInDto);
}
