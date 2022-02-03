package com.moonsabu.mathinfo.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.moonsabu.mathinfo.domain.MathTypeDomain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="math_type_info")
public class MathTypeInfo {
	@EmbeddedId 
	public MathTypeDomain mathTypeDomain;

	
	@Column(length = 300, nullable = false)
	public String quesType;
	
	/*
	@Builder
    public MathTypeInfo(int type_id, int unitUniqNo, String quesType) {
        this.type_id = type_id;
        this.unitUniqNo = unitUniqNo;
        this.quesType = quesType;
    }
    */
}