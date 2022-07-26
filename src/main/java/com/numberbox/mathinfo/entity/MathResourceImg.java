package com.numberbox.mathinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathResourceImg {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int slideImgNo;
	
	@Column(length = 11, nullable = false)
	public int resourceNo;
	
	@Column(length = 30, nullable = false)
	public String imgPath;
	
	@Column(length = 70, nullable = false)
	public String imgName;

	/*
	@Builder
    public MathResourceCate(int resourceNo, int mainCateNo, int midCateNo){
        this.resourceNo = resourceNo;
        this.mainCateNo = mainCateNo;
        this.midCateNo = midCateNo;
    }
    */
	
}
