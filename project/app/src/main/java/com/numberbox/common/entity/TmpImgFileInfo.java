package com.numberbox.common.entity;

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
public class TmpImgFileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seqNo;

    @JsonIgnore
    UUID userUniqId;

    /*
     * 10 : 수식편집기 이미지 비동기 업로드 또는 base64 복붙으로 들어오는 경우 (저장 폴더 루트 경로는 editorImgUpld)
     * 11 : hwpToHtml 파일변환에서 들어오는 경우(저장 폴더 루트 경로는 hwpToHtml)
     */
    int actionId;

    /*
     * [imgPathCode 생성 규칙]
     * 1. imgPathCode는 8자리 코드
     * 2. 처음 두자리는 actionId
     * 3. 뒤 6자리는 연월(yyyyMM 형식)
     */
    int imgPathCode;

    String imgPath;

    String imgFileName;

    @Column(updatable = false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
    LocalDateTime sysCreateDate;

    public TmpImgFileInfo(UUID userUniqId, int actionId, int imgPathCode, String imgPath, String imgFileName) {
        this.userUniqId = userUniqId;
        this.actionId = actionId;
        this.imgPathCode = imgPathCode;
        this.imgPath = imgPath;
        this.imgFileName = imgFileName;
    }
}
