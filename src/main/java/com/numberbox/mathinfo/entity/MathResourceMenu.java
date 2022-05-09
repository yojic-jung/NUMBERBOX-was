package com.numberbox.mathinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class MathResourceMenu {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int seqNo;
	
	@Column(length = 2, nullable = false)
	public int mainCateNo;
	
	@Column(length = 20, nullable = false)
	public String mainCateName;
	
	@Column(length = 2, nullable = false)
	public int midCateNo;
	
	@Column(length = 20, nullable = false)
	public String midCateName;
}
