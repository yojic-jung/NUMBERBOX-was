package com.numberbox.convert.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
public class HwpConvertContentsStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seqNo;

    Long convertNo;

    @JsonIgnore
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    UUID userUniqId;

    String convertFileName;

    @Column(updatable = false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
    LocalDateTime sysCreateDate;

}
