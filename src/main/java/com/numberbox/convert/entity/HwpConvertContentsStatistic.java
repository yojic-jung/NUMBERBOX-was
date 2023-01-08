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
public class HwpConvertContentsStatistic {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  Long seqNo;
	  
	  Long convertNo;
	  
	  @JsonIgnore
	  @Column(columnDefinition = "BINARY(16)", nullable = false, updatable=false)
	  UUID userUniqId;
	  
	  String convertFileName;
	  
	  @Column(updatable=false)
	  @CreationTimestamp
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
	  LocalDateTime sysCreateDate;
	
}
