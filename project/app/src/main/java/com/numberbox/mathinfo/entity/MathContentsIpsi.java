package com.numberbox.mathinfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathContentsIpsi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seqNo;

    @Column(length = 11, nullable = false, updatable = false)
    int contentsNo;

    /*
     * 출제 기관 : 1 (평가원) 2 (교육청)
     */
    @Column(length = 1, nullable = false)
    int manageIns;

    @Column(length = 4, nullable = false)
    int impYear;

    @Column(length = 2, nullable = false)
    int impMonth;

    @Column(length = 2, nullable = false)
    int wrongRatio;
    /*
     * 가/나형 구분 : 1 (통합) 2 (가) 3 (나)
     */
    @Column(length = 1, nullable = true)
    int paperType;

    @Column(length = 2, nullable = false)
    int oddQuesNum;

    @Column(length = 2, nullable = true)
    int evenQuesNum;

    @Column(updatable = false)
    @CreationTimestamp
    LocalDateTime sysCreateDate;

    @Column
    @UpdateTimestamp
    LocalDateTime sysUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentsNo", insertable = false, updatable = false)
    private MathContents mathContents;

}
