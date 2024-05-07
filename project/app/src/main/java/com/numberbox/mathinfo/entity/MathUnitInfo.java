package com.numberbox.mathinfo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class MathUnitInfo {

    @Id
    public int unitUniqNo;

    @Column(length = 20, nullable = false)
    public String subject;

    @Column(length = 30, nullable = false)
    public String firUnit;

    @Column(length = 30, nullable = false)
    public String secUnit;

    @Column(length = 40, nullable = true)
    public String thrUnit;

    @Builder
    public MathUnitInfo(int unitUniqNo, String subject, String firUnit, String secUnit, String thrUnit) {
        this.unitUniqNo = unitUniqNo;
        this.subject = subject;
        this.firUnit = firUnit;
        this.secUnit = secUnit;
        this.thrUnit = thrUnit;
    }
}
