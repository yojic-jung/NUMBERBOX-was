package com.kamcci.modules.logging.engine.advice

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.modules.logging.control.service.RequestLoggingService
import com.kamcci.modules.logging.control.service.ResponseLoggingService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Aspect
@Component
class ClientLoggingAspect(
    private val reqLoggingService: RequestLoggingService,
    private val resLoggingService: ResponseLoggingService,
    private val eventPublisher: ApplicationEventPublisher
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * pointCut이 적용된 메서드 실행전 request 정보를 추출하고 실행 후 response 정보를 추출하여 이벤트 발행
     */
    // @RequestMapping, @GetMapping 등 모든 HTTP 메서드의 요청을 가로챔
    @Around(
        "@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
                "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
                "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
                "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) " +
                "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
                "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)"
    )
    fun logRequestAndResponse(joinPoint: ProceedingJoinPoint): Any? {
        // request logging
        val requestLoggingDto = try {
            reqLoggingService.logging()
        } catch (e: Exception) {
            log.warn("request 로깅중 예외 발생 : $e")
            null
        }

        // 메서드 실행
        val returnValue = joinPoint.proceed()

        // response logging
        val responseLoggingDto = try {
            resLoggingService.logging(returnValue)
        } catch (e: Exception) {
            log.warn("response 로깅중 예외 발생 : $e")
            null
        }

        // req & res 로깅 정보 발행
        try {
            if (requestLoggingDto != null && responseLoggingDto != null) {
                eventPublisher.publishEvent(
                    ClientLoggingInfoEventDto(
                        requestLoggingDto,
                        responseLoggingDto
                    )
                )
            }
        } catch (e: Exception) {
            log.warn("request, response 로깅 이벤트 발행 중 예외 발생 : $e")
        }

        return returnValue
    }
}
