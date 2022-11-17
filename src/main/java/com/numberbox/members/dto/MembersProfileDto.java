package com.numberbox.members.dto;

import java.util.UUID;

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
    public int hwpDownCnt;
    
    public MembersProfile toEntity() {
		return MembersProfile.builder().userNo(userNo).userUniqId(userUniqId).nickname(nickname).hwpDownCnt(hwpDownCnt)
				.build();
	}
}
