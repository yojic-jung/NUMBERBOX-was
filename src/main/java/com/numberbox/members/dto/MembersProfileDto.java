package com.numberbox.members.dto;

import java.util.UUID;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numberbox.members.entity.MembersProfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembersProfileDto {

	private long userNo;
	@JsonIgnore
	private UUID userUniqId;
	public String nickname;
	public String profileImgName;
	public String profileImgPath;
	public MultipartFile profileImgFile;

	/*
	 * 0 : 미등록 1 : 원장 2 : 강사 3 : 교사 4 : 학부모 5 : 학생 6 : 기타
	 */
	public int profileType;
	@JsonIgnore
	public int hwpDownCnt;
	@JsonIgnore
	public int unitMappingCnt;
	@JsonIgnore
	public int aiContentsCnt;

	public MembersProfile toEntity() {
		return MembersProfile.builder().userNo(userNo).userUniqId(userUniqId).nickname(nickname).build();
	}
}
