package com.kamcci.numberbox.infra.orm.entity.member

import com.kamcci.numberbox.app.domain.enumeration.ProfileType
import jakarta.persistence.*
import java.util.*

/**
 * 회원 프로필
 */
@Table(name = "members_profile")
@Entity
class MemberProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    var id: Long = 0

    /**
     * MemberEntity.userUniqId
     */
    @Column(name = "user_uniq_id", columnDefinition = "BINARY(16)", updatable = false)
    var userUniqId: UUID? = null

    /**
     * 별명
     */
    @Column(length = 24, nullable = false)
    var nickname: String? = null

    /**
     * 프로필 이미지 파일명
     */
    @Column(length = 70, nullable = true)
    var profileImgName: String? = null

    /**
     * 프로필 이미지 경로
     */
    @Column(length = 30, nullable = true)
    var profileImgPath: String? = null

    /**
     * 프로필 타입
     */
    @Column(length = 1, nullable = false)
    var profileType: ProfileType = ProfileType.None

    /**
     * 한글 문서 다운로드 사용량
     */
    @Column(length = 1, nullable = false)
    var hwpDownCnt: Int = 0

    /**
     * AI 단원 매핑 일일 사용량
     */
    @Column(length = 1, nullable = false)
    var unitMappingCnt: Int = 0

    /**
     * AI 유사 문제 제작 사용량
     */
    @Column(length = 1, nullable = false)
    var aiContentsCnt: Int = 0
}