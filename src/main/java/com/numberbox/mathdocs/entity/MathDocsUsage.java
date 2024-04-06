package com.numberbox.mathdocs.entity;

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

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathDocsUsage { // 사용자들이 학습지를 얼마나 만드는지 체크하기 위한 엔티티, 추후 삭제해도 됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int docsNo;

    @Column(length = 700, nullable = false)
    String contentsNoList;

    @JsonIgnore
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    UUID userUniqId;

    @Column(length = 7, nullable = true)
    String docsGrade;

    @Column(length = 20, nullable = true)
    String docsTitle;

    @Column(length = 50, nullable = true)
    String docsSubTitle;

    @Column(length = 20, nullable = true)
    String docsOwner;

    @Column(updatable = false)
    @CreationTimestamp
    LocalDateTime sysCreateDate;

    @Column
    @UpdateTimestamp
    LocalDateTime sysUpdateDate;

}
