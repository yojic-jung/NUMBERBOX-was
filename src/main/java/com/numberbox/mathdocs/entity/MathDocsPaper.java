package com.numberbox.mathdocs.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathDocsPaper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int docsNo;

	@Column(length = 700, nullable = false)
	String contentsNoList;

	@JsonIgnore
	@Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
	UUID userUniqId;

	@Column(length = 7, nullable = true)
	String docsGrade;

	@Column(length = 20, nullable = true)
	String docsTitle;

	@Column(length = 50, nullable = true)
	String docsSubTitle;

	@Column(length = 20, nullable = true)
	String docsOwner;

	/*
	 * 0: 정상 1: 사용자가 직접 오류 신고한 경우 2: 오류 신고한 학습지 삭제한 경우 또는 학습지 생성 도중 에러 발생하여 생성되지 않아
	 * 사용자가 신고한 경우(오류 해결 후 삭제 처리) 3: 나의 제작문제로 학습지 생성한 경우(나의 학습지에서 사용자에 보이지 않고, 배치로
	 * 삭제)
	 */
	@Column(length = 1, nullable = false)
	int docsErrStts;

	@Column(updatable = false)
	@CreationTimestamp
	LocalDateTime sysCreateDate;

	@Column
	@UpdateTimestamp
	LocalDateTime sysUpdateDate;

}
