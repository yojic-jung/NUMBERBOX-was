package com.numberbox.mathinfo.dto;

import com.numberbox.mathinfo.entity.MathResourceCate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathResourceCateDto {

	public int resourceNo;

	public int mainCateNo;

	public int midCateNo;

	public MathResourceCate toEntity() {
		return MathResourceCate.builder().resourceNo(resourceNo).mainCateNo(mainCateNo).midCateNo(midCateNo).build();
	}
}
