package com.numberbox.restapi.image.dto.request;

import com.numberbox.appdomain.enumuration.image.ImagePathType;
import org.springframework.web.multipart.MultipartFile;

public record ImgFileRequest(ImagePathType imagePathType, MultipartFile multipartFile) {
}
