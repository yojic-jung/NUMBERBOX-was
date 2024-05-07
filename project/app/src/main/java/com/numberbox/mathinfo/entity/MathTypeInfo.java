package com.numberbox.mathinfo.entity;

import com.numberbox.mathinfo.domain.MathTypeDomain;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "math_type_info")
public class MathTypeInfo {

    @EmbeddedId
    public MathTypeDomain mathTypeDomain;

    @Column(length = 1500, nullable = false)
    public String quesType;

    @Column(length = 2, nullable = false)
    public int typeOrder;

    /*
     * @Builder public MathTypeInfo(int type_id, int unitUniqNo, String quesType) {
     * this.type_id = type_id; this.unitUniqNo = unitUniqNo; this.quesType =
     * quesType; }
     */
}