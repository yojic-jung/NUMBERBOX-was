package com.numberbox.common.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImgFileModel {

	public int actionId;
	public String imgPath;
	public MultipartFile multipartFile;

}
