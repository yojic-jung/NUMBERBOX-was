package com.numberbox.mathinfo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.CreationTimestamp;

import com.numberbox.mathinfo.domain.MathConRepoDomain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathConRepoInfo {
	@EmbeddedId
	MathConRepoDomain mathConRepoDomain;
	
	@Column(updatable=false)
	@CreationTimestamp
	LocalDateTime sysCreateDate;
}
