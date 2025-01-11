package com.kamcci.modules.logging.engine.advice

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.modules.logging.control.service.RequestLoggingService
import com.kamcci.modules.logging.control.service.ResponseLoggingService
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher

class ClientLoggingAdvice(
    private val reqLoggingService: RequestLoggingService,
    private val resLoggingService: ResponseLoggingService,
    private val eventPublisher: ApplicationEventPublisher
) : MethodInterceptor {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun invoke(invocation: MethodInvocation): Any? {
        // request logging
        val requestLoggingDto = try {
            reqLoggingService.logging()
        } catch (e: Exception) {
            log.warn("exception: $e")
            null
        }

        // 타깃 메서드 실행
        val returnValue = invocation.proceed()

        // reponse loggin
        val responseLoggingDto = try {
            resLoggingService.logging()
        } catch (e: Exception) {
            log.warn("exception: $e")
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
            log.warn("exception: $e")
        }

        return returnValue
    }
}