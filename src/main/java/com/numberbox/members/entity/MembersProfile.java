package com.numberbox.members.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members_profile")
public class MembersProfile implements Serializable { // 조인시 pk로 조인 하지 않는 경우 implements Serializable 달아야함
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long userNo;

    @JsonIgnore
    @Column(name = "userUniqId", columnDefinition = "BINARY(16)", updatable = false)
    private UUID userUniqId;

    @Column(length = 24, nullable = false)
    public String nickname;

    @Column(length = 70, nullable = true, insertable = false, updatable = false)
    public String profileImgName;

    @Column(length = 30, nullable = true, insertable = false, updatable = false)
    public String profileImgPath;

    @Column(length = 1, nullable = false, insertable = false, updatable = false)
    public int profileType;

    @Column(length = 1, nullable = false, insertable = false, updatable = false)
    public int hwpDownCnt;

    @Column(length = 1, nullable = false, insertable = false, updatable = false)
    public int unitMappingCnt;

    @Column(length = 1, nullable = false, insertable = false, updatable = false)
    public int aiContentsCnt;
}
