package com.numberbox.mathinfo.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class MathTypeDomain  implements Serializable {
	public String typeNo; 
	public String unitUniqNo;
	
	public MathTypeDomain() {}
	
	@Builder
	public MathTypeDomain(String typeNo, String unitUniqNo) { 
		this.typeNo = typeNo;
		this.unitUniqNo = unitUniqNo; 
	}
}