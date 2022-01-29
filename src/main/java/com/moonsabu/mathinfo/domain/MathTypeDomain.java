package com.moonsabu.mathinfo.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
public class MathTypeDomain  implements Serializable {
	private String typeNo; 
	private String unitUniqNo;
	
	@Builder
	public MathTypeDomain(String typeNo, String unitUniqNo) { 
		this.typeNo = typeNo;
		this.unitUniqNo = unitUniqNo; 
	}
}
