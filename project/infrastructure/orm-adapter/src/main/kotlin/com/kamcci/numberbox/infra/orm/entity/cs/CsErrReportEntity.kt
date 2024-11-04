package com.kamcci.numberbox.infra.orm.entity.cs

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "cs_err_report")
class CsErrReportEntity {
    @Id
    var id: Int = 0

    /*
     * errType==0 : 기타 오류신고 errType==1 : 문제 오류신고 errType==2 : 컨텐츠 오류신고 errType==3 :
     * 학습지 오류신고 errType==4 : 문제 만들기 페이지 오류신고 errType==5 : hwp to web 파일변환기 오류신고
     */
    @Column(length = 1, nullable = false)
    var errType: Int = 0

    @Column(length = 11, nullable = true, updatable = false)
    var contentsNo: Int = 0

    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var reportUser: UUID? = null

    @Column(length = 500, nullable = true)
    var reportContents: String? = null

    @Column(columnDefinition = "BINARY(16)", nullable = true)
    var replyUser: UUID? = null

    @Column(length = 500, nullable = true)
    var replyContents: String? = null

    @Column(length = 7, nullable = false)
    var osInfo: String? = null

    @Column(length = 7, nullable = false)
    var browser: String? = null

    @Column(length = 30, nullable = true)
    var firstImgPath: String? = null

    @Column(length = 70, nullable = true)
    var firstImgName: String? = null

    @Column(length = 30, nullable = true)
    var secondImgPath: String? = null

    @Column(length = 70, nullable = true)
    var secondImgName: String? = null

    @Column(length = 30, nullable = true)
    var thirdImgPath: String? = null

    @Column(length = 70, nullable = true)
    var thirdImgName: String? = null

    /*
     * 접수 : 0 답변완료 : 1
     */
    @Column(length = 1, nullable = false)
    var reportStts: Int = 0

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null
}