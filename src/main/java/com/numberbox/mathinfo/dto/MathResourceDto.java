package com.numberbox.mathinfo.dto;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.numberbox.mathinfo.entity.MathResource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathResourceDto {
	
	public int resourceNo;
	
	public UUID userUniqId;
	
	public String title;
	public String description;
	

	public String cateList;
	
	public String imgPath;
	public String imgName;
	public MultipartFile imgFile;
	
	public String pptPath;
	public String pptName;
	public MultipartFile pptFile;
	
	public int downCnt;
	
	public MathResource toEntity() {
		return MathResource.builder().resourceNo(resourceNo).userUniqId(userUniqId).title(title).description(description)
										.imgPath(imgPath).imgName(imgName).pptPath(pptPath).pptName(pptName)
										.downCnt(downCnt)
										.build();
	}
}
