package com.kamcci.numberbox.mathinfo.entity;

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
public class MathContentsLicense {
    @Id
    int contentsNo;
    @Column(length = 1, nullable = false)
    int onlineLicStts;
    @Column(length = 1, nullable = false)
    int perLicStts;
    @Column(length = 6, nullable = true)
    int perLicPrice;
    @Column(length = 1, nullable = false)
    int entLicStts;
    @Column(length = 6, nullable = true)
    int entLicPrice;
    @Column(length = 1, nullable = false)
    int shareStts;

    @Column(updatable = false)
    @CreationTimestamp
    LocalDateTime sysCreateDate;
    @Column
    @UpdateTimestamp
    LocalDateTime sysUpdateDate;

    @ManyToOne
    @JoinColumn(name = "contentsNo", referencedColumnName = "contentsNo", insertable = false, updatable = false)
    MathContents mathContents;
}
