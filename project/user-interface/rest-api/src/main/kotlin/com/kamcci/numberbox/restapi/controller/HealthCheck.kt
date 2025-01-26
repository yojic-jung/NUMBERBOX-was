package com.kamcci.numberbox.restapi.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheck {
    @RequestMapping("/public/health", method = [RequestMethod.HEAD])
    fun healthCheck() {
        // 무중단 배포를 위한 헬스 체크 엔드포인트
    }
}