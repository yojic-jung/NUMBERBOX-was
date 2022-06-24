package com.numberbox.mathinfo.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.numberbox.mathinfo.domain.MathConLikeDomain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathConLikeInfo {
	@EmbeddedId
	MathConLikeDomain mathConLikeDomain;
}
