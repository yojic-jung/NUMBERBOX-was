package com.numberbox.mathinfo.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class MathConRepoDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int contentsNo;

	@JsonIgnore
	public UUID userUniqId;

	public MathConRepoDomain() {
	}

	@Builder
	public MathConRepoDomain(int contentsNo, UUID userUniqId) {
		this.contentsNo = contentsNo;
		this.userUniqId = userUniqId;
	}
}
