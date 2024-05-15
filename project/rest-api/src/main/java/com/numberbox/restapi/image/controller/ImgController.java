package com.numberbox.restapi.image.controller;

import com.numberbox.appdomain.dto.image.ImgFileInDto;
import com.numberbox.appusecase.image.port.in.ImgRegisterUseCase;
import com.numberbox.auth.control.annotation.UserInfo;
import com.numberbox.auth.control.dto.Client;
import com.numberbox.restapi.image.dto.request.ImgFileRequest;
import com.numberbox.restapi.image.mapper.ImgFileMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/common")
public class ImgController {
    private final ImgRegisterUseCase imgRegisterUseCase;
    private final ImgFileMapper imgFileMapper;

    public ImgController(ImgRegisterUseCase imgRegisterUseCase, ImgFileMapper imgFileMapper) {
        this.imgRegisterUseCase = imgRegisterUseCase;
        this.imgFileMapper = imgFileMapper;
    }


    // todo 에디터 이미지임
    @PostMapping("/imgUpload")
    public Map<String, Object> imgUpload(@UserInfo Client client, ImgFileRequest imgFileRequest) throws IOException {
        // 이미지 등록
        ImgFileInDto imgFileInDto = imgFileMapper.fromRequestToDto(imgFileRequest);
        imgRegisterUseCase.register(client.userId(), imgFileInDto);
        return new HashMap<>();
    }

}
