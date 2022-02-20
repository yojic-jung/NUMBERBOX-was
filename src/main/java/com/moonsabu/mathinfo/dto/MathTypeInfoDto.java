package com.moonsabu.mathinfo.dto;

import javax.persistence.Table;

import com.moonsabu.mathinfo.domain.MathTypeDomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="math_type_info")
public class MathTypeInfoDto {
	public MathTypeDomain mathTypeDomain;
	public String quesType;
	

}
