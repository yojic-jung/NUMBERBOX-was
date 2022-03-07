package com.numberbox.mathinfo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
public class MathContents {
	@Id
	int contentsNo;
	@Column(length = 5, nullable = false)
	int unitUniqNo;
	@Column(length = 2, nullable = false)
	int typeNo;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	String contents;
	@Column(length = 70, nullable = true, updatable=false)
	String contentsImg;
	
	@Column(columnDefinition = "TEXT", nullable = true)
	String solution;
	@Column(length = 70, nullable = true, updatable=false)
	String solutionImg;
	
	@Column(length = 30, nullable = true, updatable=false)
	String imgPath;
	
	@Column(length = 3000, nullable = true)
	String firNo;
	@Column(length = 3000, nullable = true)
	String secNo;
	@Column(length = 3000, nullable = true)
	String thrNo;
	@Column(length = 3000, nullable = true)
	String fourNo;
	@Column(length = 3000, nullable = true)
	String fifNo;
	
	@Column(length = 1, nullable = false)
	String multiChoiceType;
	
	@Column(length = 3000, nullable = true)
	String answer;
	@Column(length = 9, nullable = true)
	String choiceAnswer;					//전체 체크해서 바이트 체크
	
	@Column(length = 10, nullable = false)
	String workMem;
	
	@Column(length =11, nullable = false)
	int likeCnt;
	@Column(length =11, nullable = false)
	int hateCnt;
	@Column(length =11, nullable = false)
	int downCnt;
	
	@Column(length =20, nullable = false)
	String originRef;
	@Column(length =4, nullable = false)
	int originNo;
	@Column(length =1, nullable = false)
	int quesLevel;
	@Column(length =1, nullable = false)
	int ansExistStts;
	@Column(length =1, nullable = false)
	int svcPosbStts;
	
	@Column(updatable=false)
	@CreationTimestamp
	LocalDateTime sysCreateDate;
	@Column
	@UpdateTimestamp
	LocalDateTime sysUpdateDate;
	

}
