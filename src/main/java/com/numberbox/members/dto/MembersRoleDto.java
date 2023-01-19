package com.numberbox.members.dto;


import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numberbox.members.entity.MembersRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembersRoleDto {
	
	private long seqNo;
	@JsonIgnore
	private UUID userUniqId;
	private boolean enabled;
    private String roleName;
    
    public MembersRole toEntity() {
		return MembersRole.builder().seqNo(seqNo).userUniqId(userUniqId).enabled(enabled).roleName(roleName)
				.build();
	}
}
