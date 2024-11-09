package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

/**
 * 수학 학습지 제작 사용량
 */
@Entity
@Table(name = "math_docs_usage")
class MathDocsUsageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0

    @Column(length = 700, nullable = false)
    var contentsIdList: String? = null

    // 학습지 제작자 id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    var memberId: UUID? = null

    // 학년
    @Column(length = 7, nullable = true)
    var docsGrade: String? = null

    // 학습지 제목
    @Column(length = 20, nullable = true)
    var docsTitle: String? = null

    // 학습지 부제목
    @Column(length = 50, nullable = true)
    var docsSubTitle: String? = null

    // 출제자
    @Column(length = 20, nullable = true)
    var docsOwner: String? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

}