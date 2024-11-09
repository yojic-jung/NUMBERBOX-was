package com.kamcci.numberbox.infra.orm.entity.docs

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

/**
 * 수학 학습지 제작 사용량
 */
@Entity
@Table(name = "math_docs_paper")
class MathDocsUsageEntity {
    // 사용자들이 학습지를 얼마나 만드는지 체크하기 위한 엔티티, 추후 삭제해도 됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var docsNo: Int = 0

    @Column(length = 700, nullable = false)
    var contentsNoList: String? = null

    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var userUniqId: UUID? = null

    @Column(length = 7, nullable = true)
    var docsGrade: String? = null

    @Column(length = 20, nullable = true)
    var docsTitle: String? = null

    @Column(length = 50, nullable = true)
    var docsSubTitle: String? = null

    @Column(length = 20, nullable = true)
    var docsOwner: String? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

}