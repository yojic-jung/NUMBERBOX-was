package com.numberbox.members.dto;

import java.util.UUID;

import com.numberbox.members.entity.MembersNo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembersNoDto {
	
	private long userNo;
    private UUID userUniqId;
   
    public MembersNo toEntity() {
		return MembersNo.builder().userNo(userNo).userUniqId(userUniqId)
				.build();
	}
}
