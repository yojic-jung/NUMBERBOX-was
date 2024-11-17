package com.kamcci.numberbox.mathinfo.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class MathTypeDomain implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String typeNo;

    public String unitUniqNo;

    public MathTypeDomain() {

    }

    @Builder
    public MathTypeDomain(String typeNo, String unitUniqNo) {
        this.typeNo = typeNo;
        this.unitUniqNo = unitUniqNo;
    }
}