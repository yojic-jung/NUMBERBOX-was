package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.*

/**
 * 한글 파일 변환 정보
 */
@Entity
@Table(name = "hwp_convert_file")
class HwpConvertFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @NotNull
    @Column(name = "member_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var memberId: UUID? = null

    // 파일 변환 완료 여부
    @NotNull
    @Column(name = "convert_type", nullable = false)
    var convertType: HwpConvertFileType? = null

    // 원본 파일명
    @Column(name = "origin_file_name", length = 100)
    var originFileName: String? = null

    // 변환 파일명
    @Column(name = "convert_file_name", length = 100)
    var convertFileName: String? = null

    // 요청 시간
    @CreationTimestamp
    @NotNull
    @Column(name = "request_at", nullable = false)
    var requestAt: LocalDateTime = LocalDateTime.now()

    // 변환 완료 시간
    @NotNull
    @Column(name = "convert_at", nullable = false)
    var convertAt: LocalDateTime? = null

    // 삭제 시간
    @NotNull
    @Column(name = "deleted_at", nullable = false)
    var deletedAt: LocalDateTime? = null
}