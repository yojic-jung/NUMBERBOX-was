package com.numberbox.convert.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numberbox.convert.entity.HwpConvertContents;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HwpConvertContentsDto {
    Long convertNo;

    @JsonIgnore
    UUID userUniqId;

    boolean converted;

    String convertFileName;
    String convertContents;

    String imgPath;

    List<String> imgFileTagList;

    boolean errStts;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
    LocalDateTime sysCreateDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일", timezone = "Asia/Seoul")
    LocalDateTime sysUpdateDate;

    public HwpConvertContents toEntity() {
        return HwpConvertContents.builder()
                .convertNo(convertNo)
                .userUniqId(userUniqId)
                .converted(converted)
                .convertFileName(convertFileName)
                .convertContents(convertContents)
                .imgPath(imgPath)
                .errStts(errStts)
                .build();
    }
}
