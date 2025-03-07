package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

/**
 * 고객센터 문의 내역
 */
@Entity
@Table(name = "cs_error_report")
class CsErrorReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0

    // 오류 타입
    @Column(length = 1, nullable = false)
    var errType: CSErrorType? = null

    /**
     * errType = MathContents -> math_contents.id
     * errType = MathResource -> math_resource.id
     * errType = MathDocs -> math_docs_paper.id
     * errType = HwpConvert -> hwp_convert_contents.id
     * 이외 null
     */
    @Column(length = 11, nullable = true, updatable = false)
    var contentsId: Long? = null

    // 문의자 member.id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var reportMemberId: UUID? = null

    // 문의 내용
    @Column(length = 500, nullable = true)
    var reportContents: String? = null

    // 답변자 member.id
    @Column(columnDefinition = "BINARY(16)", nullable = true)
    var replyMemberId: UUID? = null

    // 답변 내용
    @Column(length = 500, nullable = true)
    var replyContents: String? = null

    // 사용자 os
    @Column(length = 7, nullable = false)
    var clientOs: OsType? = null

    // 사용자 브라우저
    @Column(length = 7, nullable = false)
    var clientBrowser: BrowserType? = null

    // 참고 이미지1 - 경로
    @Column(length = 30, nullable = true)
    var firstImgPath: String? = null

    // 참고 이미지1 - 이름
    @Column(length = 70, nullable = true)
    var firstImgName: String? = null

    // 참고 이미지2 - 경로
    @Column(length = 30, nullable = true)
    var secondImgPath: String? = null

    // 참고 이미지2 - 이름
    @Column(length = 70, nullable = true)
    var secondImgName: String? = null

    // 참고 이미지3- 경로
    @Column(length = 30, nullable = true)
    var thirdImgPath: String? = null

    // 참고 이미지3 - 이름
    @Column(length = 70, nullable = true)
    var thirdImgName: String? = null

    // 접수 상태
    @Column(length = 1, nullable = false)
    var reportStts: ReportSttsType? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null
}