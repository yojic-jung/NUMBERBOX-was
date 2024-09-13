package com.kamcci.modules.system.construction.tx.config

import com.kamcci.modules.system.construction.tx.advice.SystemConstructionTXAdvice
import com.kamcci.modules.system.construction.tx.config.CustomTxUserConfig.CUSTOM_TX_ANNOTATION
import org.aopalliance.intercept.MethodInterceptor
import org.springframework.aop.Advisor
import org.springframework.aop.Pointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SystemConstructionTxConfig(
    private val txManager: PlatformTransactionManager,
) {
    @Bean
    @Qualifier("txMethod")
    fun txAnnotationPointcut(): Pointcut {
        return AnnotationMatchingPointcut(null, CUSTOM_TX_ANNOTATION.java)
    }

    @Qualifier("txClass")
    @Bean
    fun customTransactionAdvice(): MethodInterceptor {
        val customTxAdvice = SystemConstructionTXAdvice(txManager)
        return customTxAdvice
    }

    @Bean
    fun customTransactionAdvisor(): Advisor {
        return DefaultPointcutAdvisor(txAnnotationPointcut(), customTransactionAdvice())
    }
}