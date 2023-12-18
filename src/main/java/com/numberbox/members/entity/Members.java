package com.numberbox.members.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.uuid.Generators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Members {

	@Id
	@Column(columnDefinition = "BINARY(16)")
	@JsonIgnore
	private UUID userUniqId;

	@PrePersist
	public void createUserUniqId() {
		// sequential uuid 생성
		UUID uuid = Generators.timeBasedGenerator().generate();
		String[] uuidArr = uuid.toString().split("-");
		String uuidStr = uuidArr[2] + uuidArr[1] + uuidArr[0] + uuidArr[3] + uuidArr[4];
		StringBuffer sb = new StringBuffer(uuidStr);
		sb.insert(8, "-");
		sb.insert(13, "-");
		sb.insert(18, "-");
		sb.insert(23, "-");
		uuid = UUID.fromString(sb.toString());
		this.userUniqId = uuid;
	}

	private String email;
	private String password;

	/*
	 * 0 : 일반 계정 1 : 휴먼계정 2 : 탈퇴 요청 3 : 탈퇴 계정(유령계정)
	 */
	private int humanStatus;
	private int failCount;
	@CreationTimestamp
	private LocalDateTime lastFailTime;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "userUniqId", referencedColumnName = "userUniqId", updatable = false)
	List<MembersRole> role;

	// 0 : 일반계정
	// 1 : 임시 비밀번호 발급계정(임시 비밀번호 발급계정 비밀번호 수정 안 하는 경우 새로운 비밀번호로 수정(스케쥴러로 구현))
	private boolean tmpPassword;

	@CreationTimestamp
	private LocalDateTime signupDate;
	@UpdateTimestamp
	private LocalDateTime lastLoginDate;
	
	@OneToMany(mappedBy = "members", fetch = FetchType.EAGER)
	List<MembersProfile> membersProfile;
}