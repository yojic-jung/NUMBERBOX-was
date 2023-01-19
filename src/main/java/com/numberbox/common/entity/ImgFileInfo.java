package com.numberbox.common.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.uuid.Generators;

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
public class ImgFileInfo {
	  @Id
	  @Column(columnDefinition = "BINARY(16)")
	  UUID seqUuid;
	  
	  @PrePersist
	  public void createUserUniqId() {
		  //sequential uuid 생성
		  UUID uuid = Generators.timeBasedGenerator().generate();
		  String[] uuidArr = uuid.toString().split("-");
		  String uuidStr = uuidArr[2]+uuidArr[1]+uuidArr[0]+uuidArr[3]+uuidArr[4];
		  StringBuffer sb = new StringBuffer(uuidStr);
		  sb.insert(8, "-");
		  sb.insert(13, "-");
		  sb.insert(18, "-");
		  sb.insert(23, "-");
		  uuid = UUID.fromString(sb.toString());
		  this.seqUuid = uuid;
	  }
	  /*
	   *  10 : 수식편집기 이미지 비동기 업로드 또는 base64 복붙으로 들어오는 경우 (저장 폴더 루트 경로는 editorImgUpld)
	   *  11 : hwpToHtml 파일변환에서 들어오는 경우(저장 폴더 루트 경로는 hwpToHtml)
	   */
	  int actionId;
	  
	  int contentsNo;
	  
	  /*
	   *  imgPathCode 생성 규칙 : 
	   *  					1. imgPathCode는 8자리 코드
	   *  					2. 처음 두자리는 actionId	
	   *  					3. 뒤 6자리는 연월(yyyyMM 형식)
	   */
	  int imgPathCode;
	  
	  
	  String imgPath;
	  
	  String imgFileName;
	  
	  @Column(updatable=false)
	  @CreationTimestamp
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
	  LocalDateTime sysCreateDate;

}
