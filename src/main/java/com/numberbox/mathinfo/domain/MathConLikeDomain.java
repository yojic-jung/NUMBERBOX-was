package com.numberbox.mathinfo.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter 
@Setter 
public class MathConLikeDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int contentsNo;
	
	public UUID userUniqId;
	
	public MathConLikeDomain() {}
	
	@Builder
	public MathConLikeDomain(int contentsNo, UUID userUniqId) { 
		this.contentsNo = contentsNo;
		this.userUniqId = userUniqId; 
	}

}
