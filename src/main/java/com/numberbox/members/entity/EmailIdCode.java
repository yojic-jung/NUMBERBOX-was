package com.numberbox.members.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmailIdCode {
	@Id
	private String email;

	private String idCode;

	@UpdateTimestamp
	private LocalDateTime sysCreateTime;
}
