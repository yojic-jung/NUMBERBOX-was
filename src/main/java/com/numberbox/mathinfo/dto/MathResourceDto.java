package com.numberbox.mathinfo.dto;

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
	
	public int seqNo;
	
	public long userNo;
	
	public String title;
	public String description;
	
	public int mainCateNo;
	
	public int midCateNo;
	
	public String imgPath;
	public String imgName;
	public MultipartFile imgFile;
	
	public String pptPath;
	public String pptName;
	public MultipartFile pptFile;
	
	public int downCnt;
	
	public MathResource toEntity() {
		return MathResource.builder().seqNo(seqNo).userNo(userNo).title(title).description(description).mainCateNo(mainCateNo).midCateNo(midCateNo)
										.imgPath(imgPath).imgName(imgName).pptPath(pptPath).pptName(pptName)
										.downCnt(downCnt)
										.build();
	}
}
