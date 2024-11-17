package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

/**
 * 삭제 대상 유휴 파일 목록
 */
@Entity
@Table(name = "sys_garbage_file")
class SysGarbageFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var type: GarbageFileType? = null

    @Column(nullable = false)
    var path: String? = null

    @Column(nullable = false)
    var name: String? = null

    @Column(nullable = false)
    var failCnt: Int = 0

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime = LocalDateTime.now()
}