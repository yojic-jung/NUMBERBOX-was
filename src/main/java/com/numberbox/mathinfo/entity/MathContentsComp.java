package com.numberbox.mathinfo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathContentsComp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seqNo;
    @Column(length = 11, nullable = false, updatable = false)
    int contentsNo;
    @Column(length = 20, nullable = false)
    String orgSrcRef;
    @Column(length = 4, nullable = true)
    int orgSrcNo;
    @Column(length = 3, nullable = true)
    Integer orgSrcPage;
    @Column(length = 20, nullable = true)
    String copyrightYear;
    @Column(length = 20, nullable = true)
    String mathTypeClassify;

    @JsonIgnore
    @Column(columnDefinition = "BINARY(16)")
    UUID userUniqId;

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
