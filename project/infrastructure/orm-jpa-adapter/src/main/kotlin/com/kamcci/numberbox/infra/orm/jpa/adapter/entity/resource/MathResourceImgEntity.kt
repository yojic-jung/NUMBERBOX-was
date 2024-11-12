package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import jakarta.persistence.*

/**
 * MathResourceEntity의 슬라이드 이미지
 */
@Entity
@Table(name = "math_resource_img")
class MathResourceImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(length = 30, nullable = false)
    var imgPath: String? = null

    @Column(length = 70, nullable = false)
    var imgName: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", insertable = false, updatable = false)
    var mathResource: MathResourceEntity? = null
}