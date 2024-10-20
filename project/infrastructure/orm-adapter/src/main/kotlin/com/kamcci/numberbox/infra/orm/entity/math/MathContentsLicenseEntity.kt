package com.kamcci.numberbox.infra.orm.entity.math

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * 수학문제 시용자 정의 저작권 정보
 */
@Entity
@Table(name = "math_contents_license")
class MathContentsLicenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    // 온라인 공유 여부
    @Column(length = 1, nullable = false)
    var onlineLicStts: Boolean = false

    // 개인 대상 공유 여부
    @Column(length = 1, nullable = false)
    var perLicStts: Boolean = false

    // 개인 대상 판매 가격
    @Column(length = 6, nullable = true)
    var perLicPrice: Int = 0

    // 기업 대상 공유 여부
    @Column(length = 1, nullable = false)
    var entLicStts: Boolean = false

    // 기업 대상 판매 가격
    @Column(length = 6, nullable = true)
    var entLicPrice: Int = 0

    // 플랫폼 내 공유 여부
    @Column(length = 1, nullable = false)
    var shareStts: Boolean = false

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

    @ManyToOne
    @JoinColumn(name = "contents_no", referencedColumnName = "contents_no")
    var mathContents: MathContentsEntity? = null
}