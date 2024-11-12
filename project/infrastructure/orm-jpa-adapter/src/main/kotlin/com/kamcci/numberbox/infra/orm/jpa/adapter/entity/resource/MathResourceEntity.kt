package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

/**
 * 수학 자료(도형, 그래프 등) pdf
 */
@Entity
@Table(name = "math_resource")
class MathResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(columnDefinition = "BINARY(16)")
    var memberId: UUID? = null

    @Column(length = 30, nullable = false)
    var title: String? = null

    @Column(length = 30, nullable = false)
    var imgPath: String? = null

    @Column(length = 70, nullable = false)
    var imgName: String? = null

    @Column(length = 30, nullable = false)
    var pptPath: String? = null

    @Column(length = 70, nullable = false)
    var pptName: String? = null

    @Column(length = 3, nullable = false)
    var pptPageCnt: Int = 0

    @Column(length = 11, nullable = false)
    var downCnt: Int = 0

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

    // 등록 카테고리 정보
    @OneToMany(mappedBy = "mathResource", fetch = FetchType.LAZY)
    var mathResourceCate: MutableList<MathResourceCateEntity> = mutableListOf()

    // 등록 슬라이드(미리보기) 이미지 정보
    @OneToMany(mappedBy = "mathResource", fetch = FetchType.LAZY)
    var mathResourceImg: MutableList<MathResourceImgEntity> = mutableListOf()
}