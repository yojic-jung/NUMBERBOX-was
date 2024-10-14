package com.kamcci.numberbox.infra.orm.entity.math

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

/**
 * 수학문제 출처 정보
 */
@Entity
@Table(name = "math_contents_comp")
class MathContentsSourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var seqNo: Int = 0

    // 수학문제 id
    @Column(name = "contents_no", length = 11, nullable = false, updatable = false)
    var contentsId: Int? = null

    // 출처 - 교재
    @Column(length = 20, nullable = false)
    var orgSrcRef: String? = null

    // 출처 - 문제 번호
    @Column(length = 4, nullable = true)
    var orgSrcNo: Int = 0

    // 출처 - 페이지 번호
    @Column(length = 3, nullable = true)
    var orgSrcPage: Int? = null

    // 쇄 연도
    @Column(length = 20, nullable = true)
    var copyrightYear: String? = null

    // 문제 유형
    @Column(length = 20, nullable = true)
    var mathTypeClassify: String? = null

    // 제작자
    @Column(name = "user_uniq_id", columnDefinition = "BINARY(16)")
    var memberId: UUID? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

    // 수학문제
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_no", insertable = false, updatable = false)
    var mathContents: MathContentsEntity? = null
}