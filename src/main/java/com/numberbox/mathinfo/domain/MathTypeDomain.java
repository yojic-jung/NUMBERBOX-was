package com.numberbox.mathinfo.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter 
@Setter 
public class MathTypeDomain  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String typeNo; 
	public String unitUniqNo;
	
	public MathTypeDomain() {}
	
	@Builder
	public MathTypeDomain(String typeNo, String unitUniqNo) { 
		this.typeNo = typeNo;
		this.unitUniqNo = unitUniqNo; 
	}
}