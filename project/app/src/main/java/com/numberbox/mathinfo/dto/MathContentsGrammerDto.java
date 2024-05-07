package com.numberbox.mathinfo.dto;

import com.numberbox.mathinfo.entity.MathContentsGrammer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathContentsGrammerDto {

	public int contentsNo;
	public String contentsGram;

	public MathContentsGrammer toEntity() {
		return MathContentsGrammer.builder().contentsNo(contentsNo).contentsGram(contentsGram).build();
	}

}
