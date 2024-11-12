package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import jakarta.persistence.*


/**
 * MathResourceEntity의 카테고리 정보
 */
@Entity
@Table(name = "math_resource_cate")
class MathResourceCateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(length = 2, nullable = false)
    var mainCateId: Int = 0

    @Column(length = 2, nullable = false)
    var midCateId: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", insertable = false, updatable = false)
    var mathResource: MathResourceEntity? = null
}