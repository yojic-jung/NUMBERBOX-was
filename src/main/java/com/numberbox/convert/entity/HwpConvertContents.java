package com.numberbox.convert.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class HwpConvertContents {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  Long convertNo;

	  @JsonIgnore
	  @Column(columnDefinition = "BINARY(16)", nullable = false, updatable=false)
	  UUID userUniqId;
	  
	  boolean converted;
	  
	  String convertFileName;
	  String convertContents;
	  
	  @Column(updatable=false)
	  String imgPath;
	  
	  /*
	   * 변환 에러 구분 상태코드
	   * 0 : 정상
	   * 1 : 에러 존재
	   */
	  @Column(updatable=false)
	  boolean errStts;
	  
	  @Column(updatable=false)
	  @CreationTimestamp
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
	  LocalDateTime sysCreateDate;
	
	  @Column
	  @UpdateTimestamp
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
	  LocalDateTime sysUpdateDate;
}
