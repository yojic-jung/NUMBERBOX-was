package com.kamcci.numberbox.aws.s3.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AwsS3Dto {
	private String filePath;
	private MultipartFile multipartFile;
}
