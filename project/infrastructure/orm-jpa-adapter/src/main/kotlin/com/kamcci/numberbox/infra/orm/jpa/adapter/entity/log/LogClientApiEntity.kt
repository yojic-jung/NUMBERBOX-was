package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.log

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.*

/**
 * 사용자 api 요청 로깅 정보
 */
@Entity
@Table(name = "log_client_api")
class LogClientApiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0

    // 사용자 id
    @NotNull
    @Column(name = "member_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var memberId: UUID? = null

    // 브라우저
    @NotNull
    @Column(name = "browser", length = 7, nullable = false)
    var browser: String? = null

    // os
    @NotNull
    @Column(name = "os", length = 7, nullable = false)
    var os: String? = null

    // 클라이언트 ip
    @NotNull
    @Column(name = "ip", length = 15, nullable = false)
    var ip: String? = null

    // httpMethod 타입
    @NotNull
    @Column(name = "http_method", length = 5, nullable = false)
    var httpMethod: String? = null

    // uri
    @NotNull
    @Column(name = "uri", length = 500, nullable = false)
    var uri: String? = null

    @NotNull
    @Column(name = "response_code", length = 3, nullable = false)
    var responseCode: Int? = null

    // request_body
    @Column(name = "request_body", nullable = true)
    var requestBody: String? = null

    // 생성시간
    @NotNull
    @CreationTimestamp
    @Column(name = "sys_create_time", nullable = false)
    var sysCreateTime: LocalDateTime = LocalDateTime.now()
}