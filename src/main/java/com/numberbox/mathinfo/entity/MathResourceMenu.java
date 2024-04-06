package com.numberbox.mathinfo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class MathResourceMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int seqNo;

    @Column(length = 2, nullable = false)
    public int mainCateNo;

    @Column(length = 20, nullable = false)
    public String mainCateName;

    @Column(length = 2, nullable = false)
    public int midCateNo;

    @Column(length = 20, nullable = false)
    public String midCateName;

    @Column(length = 2, nullable = false)
    public int alignOrder;
}
