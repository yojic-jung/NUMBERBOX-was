package com.numberbox.mathinfo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathContentsGrammer {

    @Id
    @Column(nullable = false)
    public int contentsNo;

    @Column(length = 16000, nullable = false)
    public String contentsGram;

}
