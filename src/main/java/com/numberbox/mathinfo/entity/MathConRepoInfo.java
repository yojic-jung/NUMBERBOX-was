package com.numberbox.mathinfo.entity;

import com.numberbox.mathinfo.domain.MathConRepoDomain;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathConRepoInfo {
    @EmbeddedId
    MathConRepoDomain mathConRepoDomain;

    @Column(updatable = false)
    @CreationTimestamp
    LocalDateTime sysCreateDate;
}
