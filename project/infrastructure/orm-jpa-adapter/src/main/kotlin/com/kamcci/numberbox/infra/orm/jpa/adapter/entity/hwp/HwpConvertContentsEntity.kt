package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.hwp

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "hwp_convert_contents")
class HwpConvertContentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var memberId: UUID? = null

    // 파일 변환 완료 여부
    @NotNull
    @Column(nullable = false)
    var isConverted: Boolean = false

    // 원본 파일명
    @NotNull
    @Column(length = 70, nullable = false)
    var fileName: String? = null

    @NotNull
    @Column(nullable = true)
    var contents: String? = null

    // 파일 내부 이미지 파일 경로(파일 이름 제외 이미지 경로만)
    @Column(length = 120, nullable = false)
    var imgPath: String? = null

    // 문법 변환(클라이언트단에서 이뤄짐) 완료 여부
    @Column(updatable = false, nullable = false)
    var isGrammarConverted: Boolean = false

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column(nullable = false)
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null
}