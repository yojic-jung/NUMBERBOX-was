package com.numberbox.mathinfo.dto;

import com.numberbox.mathinfo.domain.MathTypeDomain;
import com.numberbox.mathinfo.entity.MathTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathTypeInfoDto {
	public MathTypeDomain mathTypeDomain;
	public String quesType;
	public int typeOrder;
	
	public MathTypeInfo toEntity() {
		return MathTypeInfo.builder().mathTypeDomain(mathTypeDomain).quesType(quesType).typeOrder(typeOrder)
				.build();
	}
}
