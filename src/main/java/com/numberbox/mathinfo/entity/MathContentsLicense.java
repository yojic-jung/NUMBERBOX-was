package com.numberbox.mathinfo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathContentsLicense {
	@Id
	int contentsNo;
	@Column(length = 1, nullable = false)
	int onlineLicStts;
	@Column(length = 1, nullable = false)
	int perLicStts;
	@Column(length = 6, nullable = true)
	int perLicPrice;
	@Column(length = 1, nullable = false)
	int entLicStts;
	@Column(length = 6, nullable = true)
	int entLicPrice;
	@Column(length = 1, nullable = false)
	int shareStts;

	@Column(updatable = false)
	@CreationTimestamp
	LocalDateTime sysCreateDate;
	@Column
	@UpdateTimestamp
	LocalDateTime sysUpdateDate;

	@ManyToOne
	@JoinColumn(name = "contentsNo", referencedColumnName = "contentsNo", insertable = false, updatable = false)
	MathContents mathContents;
}
