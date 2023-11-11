package com.numberbox.members.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.CreationTimestamp;

import com.numberbox.members.domain.FollowUsersDomain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MembersFollowInfo {

	@EmbeddedId
	public FollowUsersDomain followUsers;

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime sysCreateDate;

}
