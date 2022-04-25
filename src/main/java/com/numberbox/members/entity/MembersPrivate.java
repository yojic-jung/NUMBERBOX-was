package com.numberbox.members.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "members_private")
public class MembersPrivate {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID userUniqId;

    private String userName;
    private String phoneNumber;
    private String birth;
}
