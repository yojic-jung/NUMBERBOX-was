package com.numberbox.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numberbox.common.entity.ImgFileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImgFileInfoDto {
    UUID seqUuid;

    /*
     * 10 : 수식편집기 이미지 비동기 업로드 또는 base64 복붙으로 들어오는 경우 (저장 폴더 루트 경로는 editorImgUpld)
     * 11 : hwpToHtml 파일변환에서 들어오는 경우(저장 폴더 루트 경로는 hwpToHtml)
     */
    int actionId;
    int contentsNo;

    /*
     * [imgPathCode 생성 규칙]
     * 1. imgPathCode는 8자리 코드
     * 2. 처음 두자리는 actionId
     * 3. 뒤 6자리는 연월(yyyyMM 형식)
     */
    int imgPathCode;
    String imgPath;
    String imgFileName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
    LocalDateTime sysCreateDate;

    public ImgFileInfo toEntity() {
        return ImgFileInfo.builder().actionId(actionId).contentsNo(contentsNo).imgPathCode(imgPathCode).imgPath(imgPath)
                .imgFileName(imgFileName).build();
    }
}
