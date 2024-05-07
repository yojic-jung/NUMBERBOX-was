package com.numberbox.mathinfo.dto;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	public UUID userUniqId;

	public String title;

	public String cateList;

	public String imgPath;
	public String imgName;
	public MultipartFile imgFile;

	public String pptPath;
	public String pptName;
	public int pptPageCnt;

	public String pptFistSlideImgName;

	public MultipartFile pptFile;

	public int downCnt;

	public List<MathResourceCateDto> mathResourceCate;

	public MathResource toEntity() {
		return MathResource.builder().resourceNo(resourceNo).userUniqId(userUniqId).title(title).imgPath(imgPath)
				.imgName(imgName).pptPath(pptPath).pptName(pptName).pptPageCnt(pptPageCnt).downCnt(downCnt).build();
	}
}
