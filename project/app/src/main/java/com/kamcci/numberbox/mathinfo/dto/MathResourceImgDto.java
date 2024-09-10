package com.kamcci.numberbox.mathinfo.dto;

import com.kamcci.numberbox.mathinfo.entity.MathResourceImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathResourceImgDto {

	public int slideImgNo;
	public int resourceNo;
	public String imgPath;
	public String imgName;

	public MathResourceImg toEntity() {
		return MathResourceImg.builder().resourceNo(resourceNo).imgPath(imgPath).imgName(imgName).build();
	}
}
