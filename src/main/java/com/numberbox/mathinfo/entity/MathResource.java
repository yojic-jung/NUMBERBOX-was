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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int resourceNo;
	
	@Column(length = 11, nullable = false)
	public long userNo;
	
	@Column(length = 30, nullable = false)
	public String title;
	
	@Column(length = 60)
	public String description;
	
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
    public MathResource(int resourceNo, long userNo, String title, String description, 
    		String imgPath, String imgName, String pptPath, String pptName,
    		int downCnt) {
        this.resourceNo = resourceNo;
        this.userNo = userNo;
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.imgName = imgName;
        this.pptPath = pptPath;
        this.pptName = pptName;
        this.downCnt = downCnt;
    }
}
