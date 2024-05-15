package com.numberbox.appdomain.dto.image;

public record ImgFileMetaInfoDto(
        int actionId,
        int imgPathCode,
        String imgPath,
        String imgFileName
) {
    public String getImgFileFullName() {
        return imgPath + "/" + imgFileName;
    }
}