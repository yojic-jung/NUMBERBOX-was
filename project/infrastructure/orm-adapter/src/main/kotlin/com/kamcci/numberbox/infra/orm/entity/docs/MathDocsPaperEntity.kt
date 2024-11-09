package com.kamcci.numberbox.infra.orm.entity.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsErrStatusType
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

    @Column(name = "contents_id_list", length = 700, nullable = false)
    var contentsIdList: String? = null

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

    /*
     * 0: 정상
     * 1: 사용자가 직접 오류 신고한 경우
     * 2: 오류 신고한 학습지 삭제한 경우 또는 학습지 생성 도중 에러 발생하여 생성되지 않아 사용자가 신고한 경우(오류 해결 후 삭제 처리)
     * 3: 나의 제작문제로 학습지 생성한 경우(나의 학습지에서 사용자에 보이지 않고, 배치로 삭제)
     */
    @Column(length = 1, nullable = false)
    var docsErrStts: DocsErrStatusType? = DocsErrStatusType.None

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null
}