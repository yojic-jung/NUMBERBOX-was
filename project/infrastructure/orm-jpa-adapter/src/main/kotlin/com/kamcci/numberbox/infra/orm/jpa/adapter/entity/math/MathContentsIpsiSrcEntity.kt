package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiManageInsType
import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * 입시 수학문제 출처 정보
 */
@Entity
@Table(name = "math_con_ipsi_src")
class MathContentsIpsiSrcEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0

    // 출제 기관
    @Column(name = "manage_ins", length = 1, nullable = false)
    var manageIns: IpsiManageInsType? = null

    // 출제 연도
    @Column(length = 4, nullable = false)
    var impYear: Int? = null

    // 출제 월
    @Column(length = 2, nullable = false)
    var impMonth: Int? = null

    // 오답률
    @Column(length = 2, nullable = false)
    var wrongRatio: Int? = null

    /*
     * 가/나형 구분 : 1 (통합) 2 (가) 3 (나)
     */
    @Column(length = 1, nullable = true)
    var paperType: IpsiPaperType? = null

    // 홀수형 번호
    @Column(length = 2, nullable = false)
    var oddQuesNum: Int? = null

    // 짝수형 번호
    @Column(length = 2, nullable = true)
    var evenQuesNum: Int? = null

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime = LocalDateTime.now()

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime = LocalDateTime.now()

    // 수학 문제
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", insertable = false, updatable = false)
    var mathContents: MathContentsEntity? = null
}