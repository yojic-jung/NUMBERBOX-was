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

    @Column(name = "member_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var memberId: UUID? = null

    // 파일 변환 완료 여부
    @NotNull
    @Column(name = "is_converted", nullable = false)
    var isConverted: Boolean = true

    // 원본 파일명
    @NotNull
    @Column(name = "file_name", length = 70, nullable = false)
    var fileName: String? = null

    // xhtml 컨텐츠 내용 - html 스크립트 문법 문자열
    @NotNull
    @Column(name = "contents", nullable = true)
    var contents: String? = null

    // xhtml 내부 이미지 파일 경로(파일 이름 제외 이미지 경로만)
    @Column(name = "img_path", length = 120, nullable = false)
    var imgPath: String? = null

    // 문법 변환(클라이언트단에서 이뤄짐) 완료 여부
    @Column(name = "is_grammar_converted", updatable = false, nullable = false)
    var isGrammarConverted: Boolean = true

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column(nullable = false)
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null
}