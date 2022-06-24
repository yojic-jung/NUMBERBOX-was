package com.numberbox.members.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members_role")
public class MembersRole {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long seqNo;

	@Column(columnDefinition = "BINARY(16)", updatable=false)
	private UUID userUniqId;
	
    private boolean enabled;
    private String roleName;

}