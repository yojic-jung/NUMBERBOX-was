package com.numberbox.members.dto;

import java.util.UUID;

import com.numberbox.members.entity.MembersInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MebersInfoDto {

	private UUID userUniqId;
	private String userName;
	private String birth;
    private String phoneNumber;
   
    public MembersInfo toEntity() {
		return MembersInfo.builder().userUniqId(userUniqId).userName(userName).birth(birth).phoneNumber(phoneNumber)
				.build();
	}
}
