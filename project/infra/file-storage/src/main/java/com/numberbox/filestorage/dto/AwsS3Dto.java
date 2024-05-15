package com.numberbox.filestorage.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AwsS3Dto {
    private String filePath;
    private MultipartFile multipartFile;
}
