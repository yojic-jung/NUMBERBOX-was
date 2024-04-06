package com.numberbox.mathinfo.entity;

import com.numberbox.mathinfo.domain.MathConLikeDomain;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
