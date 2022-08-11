package com.numberbox.members.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numberbox.members.entity.MembersPrivate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembersPrivateDto {
	@JsonIgnore
	private UUID userUniqId;
	
	private String userName;
	private String birth;
    private String phoneNumber;
   
    public MembersPrivate toEntity() {
		return MembersPrivate.builder().userUniqId(userUniqId).userName(userName).birth(birth).phoneNumber(phoneNumber)
				.build();
	}
}
