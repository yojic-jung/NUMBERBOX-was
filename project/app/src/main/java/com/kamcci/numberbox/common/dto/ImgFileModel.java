package com.kamcci.numberbox.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImgFileModel {

    public int actionId;
    public String imgPath;
    public MultipartFile multipartFile;

}