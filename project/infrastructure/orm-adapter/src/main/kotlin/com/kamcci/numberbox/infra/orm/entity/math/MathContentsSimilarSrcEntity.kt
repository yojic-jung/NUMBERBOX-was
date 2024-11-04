package com.kamcci.numberbox.infra.orm.entity.math

import com.kamcci.numberbox.app.domain.enumeration.math.MathTypeClassifyType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * 유사문제 출처 정보
 */
@Entity
@Table(name = "math_con_similar_src")
class MathContentsSimilarSrcEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0

    // 수학문제 id
    @Column(length = 11, nullable = false, updatable = false)
    var contentsId: Long? = null

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
    var mathTypeClassify: MathTypeClassifyType? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

    // 수학문제
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", insertable = false, updatable = false)
    var mathContents: MathContentsEntity? = null
}