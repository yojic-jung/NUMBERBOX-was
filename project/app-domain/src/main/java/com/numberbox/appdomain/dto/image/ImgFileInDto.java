package com.numberbox.appdomain.dto.image;

import com.numberbox.appdomain.enumuration.image.ImagePathType;

import java.io.InputStream;

public record ImgFileInDto(String originFileName, ImagePathType imagePathType, InputStream inputStream) {
}