package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import jakarta.persistence.*

/**
 * 수학 자료(도형, 그래프 pdf) 카테고리
 */
@Entity
@Table(name = "math_resource_menu")
class MathResourceMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    // 대분류 id
    @Column(length = 2, nullable = false)
    var mainCateId: Int = 0

    // 대분류명
    @Column(length = 20, nullable = false)
    var mainCateName: String? = null

    // 중분류 id
    @Column(length = 2, nullable = false)
    var midCateId: Int = 0

    // 중분류명
    @Column(length = 20, nullable = false)
    var midCateName: String? = null

    // 정렬 순서
    @Column(length = 2, nullable = false)
    var alignOrder: Int = 0
}