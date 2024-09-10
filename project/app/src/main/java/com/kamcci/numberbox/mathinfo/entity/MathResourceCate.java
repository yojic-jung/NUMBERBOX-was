package com.kamcci.numberbox.mathinfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathResourceCate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int seqNo;

    @Column(length = 11, nullable = false)
    public int resourceNo;

    @Column(length = 2, nullable = false)
    public int mainCateNo;

    @Column(length = 2, nullable = false)
    public int midCateNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resourceNo", referencedColumnName = "resourceNo", insertable = false, updatable = false)
    public MathResource mathResource;

    /*
     * @Builder public MathResourceCate(int resourceNo, int mainCateNo, int
     * midCateNo){ this.resourceNo = resourceNo; this.mainCateNo = mainCateNo;
     * this.midCateNo = midCateNo; }
     */

    public MathResourceCate(int resourceNo, int mainCateNo, MathResource mathResource) {
        this.resourceNo = resourceNo;
        this.mainCateNo = mainCateNo;
        this.mathResource = mathResource;
    }

}
