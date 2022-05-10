package com.numberbox.mathinfo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class MathResource {
	
	@Id
	public int seqNo;
	
	@Column(length = 11, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long userNo;
	
	@Column(length = 20, nullable = false)
	public String title;
	
	@Column(length = 60)
	public String description;
	
	@Column(length = 2, nullable = false)
	public int mainCateNo;
	
	@Column(length = 2, nullable = false)
	public int midCateNo;
	
	@Column(length = 30, nullable = false)
	public String imgPath;
	
	@Column(length = 70, nullable = false)
	public String imgName;
	
	@Column(length = 30)
	public String pptPath;
	
	@Column(length = 70)
	public String pptName;
	
	@Column(length = 11, nullable = false)
	public int downCnt;
	
	@Column(updatable=false)
	@CreationTimestamp
	LocalDateTime sysCreateDate;
	@Column
	@UpdateTimestamp
	LocalDateTime sysUpdateDate;
	
	@Builder
    public MathResource(int seqNo, long userNo, String title, String description, int mainCateNo, int midCateNo, 
    		String imgPath, String imgName, String pptPath, String pptName,
    		int downCnt) {
        this.seqNo = seqNo;
        this.userNo = userNo;
        this.title = title;
        this.description = description;
        this.mainCateNo = mainCateNo;
        this.midCateNo = midCateNo;
        this.imgPath = imgPath;
        this.imgName = imgName;
        this.pptPath = pptPath;
        this.pptName = pptName;
        this.downCnt = downCnt;
    }
}
