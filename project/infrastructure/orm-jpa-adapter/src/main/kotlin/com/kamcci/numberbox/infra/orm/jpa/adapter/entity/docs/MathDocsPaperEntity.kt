package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.infra.orm.jpa.adapter.converter.docs.ContentsIdListConverter
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

/**
 * 수학 학습지
 */
@Entity
@Table(name = "math_docs_paper")
class MathDocsPaperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0

    // 수학문제 id list
    @Convert(converter = ContentsIdListConverter::class)
    @Column(name = "contents_id_list", length = 700, nullable = false)
    var contentsIdList: MutableList<Long> = mutableListOf()

    // 학습지 소유자 id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var memberId: UUID? = null

    // 학년
    @Column(length = 7)
    var docsGrade: String? = null

    // 학습지 제목
    @Column(length = 20)
    var docsTitle: String? = null

    // 학습지 부제목
    @Column(length = 50)
    var docsSubTitle: String? = null

    // 출제자
    @Column(length = 20)
    var docsOwner: String? = null

    // 학습지 타입
    @Column(length = 1, nullable = false)
    var docsStts: DocsStatusType? = DocsStatusType.None

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

    @Column
    var sysDeleteDate: LocalDateTime? = null
}