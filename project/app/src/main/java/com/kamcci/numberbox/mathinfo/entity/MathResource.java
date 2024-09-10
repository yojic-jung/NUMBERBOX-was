package com.kamcci.numberbox.mathinfo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MathResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int resourceNo;

    @JsonIgnore
    @Column(columnDefinition = "BINARY(16)")
    public UUID userUniqId;

    @Column(length = 30, nullable = false)
    public String title;

    @Column(length = 30, nullable = false)
    public String imgPath;

    @Column(length = 70, nullable = false)
    public String imgName;

    @Column(length = 30, nullable = false)
    public String pptPath;

    @Column(length = 70, nullable = false)
    public String pptName;

    @Column(length = 3, nullable = false)
    public int pptPageCnt;

    @Column(length = 11, nullable = false)
    public int downCnt;

    @Column(updatable = false)
    @CreationTimestamp
    LocalDateTime sysCreateDate;

    @OneToMany(mappedBy = "mathResource", fetch = FetchType.LAZY)
    List<MathResourceCate> mathResourceCate;

    @Column
    @UpdateTimestamp
    LocalDateTime sysUpdateDate;

    /*
     * @Builder public MathResource(int resourceNo, UUID userUniqId, String title,
     * String description, String imgPath, String imgName, String pptPath, String
     * pptName, int downCnt) { this.resourceNo = resourceNo; this.userUniqId =
     * userUniqId; this.title = title; this.description = description; this.imgPath
     * = imgPath; this.imgName = imgName; this.pptPath = pptPath; this.pptName =
     * pptName; this.downCnt = downCnt; }
     */
}
