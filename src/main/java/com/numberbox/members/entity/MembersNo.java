package com.numberbox.members.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "members_no")
public class MembersNo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userNo;

	@Column(name="userUniqId" ,columnDefinition = "BINARY(16)")
	private UUID userUniqId;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userUniqId", insertable=false, updatable=false)
	private Members mebers;
}
