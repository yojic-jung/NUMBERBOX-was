package com.numberbox.mathinfo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class MathConRepoDomain implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public int contentsNo;

    @JsonIgnore
    public UUID userUniqId;

    public MathConRepoDomain() {
    }

    @Builder
    public MathConRepoDomain(int contentsNo, UUID userUniqId) {
        this.contentsNo = contentsNo;
        this.userUniqId = userUniqId;
    }
}
