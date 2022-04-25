package com.numberbox.members.dto;


import java.util.UUID;

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
	private UUID userUniqId;
	private boolean enabled;
    private String roleName;
    
    public MembersRole toEntity() {
		return MembersRole.builder().userUniqId(userUniqId).enabled(enabled).roleName(roleName)
				.build();
	}
}
