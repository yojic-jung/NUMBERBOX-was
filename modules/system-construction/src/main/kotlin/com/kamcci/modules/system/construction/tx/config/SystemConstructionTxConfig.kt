package com.kamcci.modules.system.construction.tx.config

import com.kamcci.modules.system.construction.common.util.FindAnnotation.getAnnotationClass
import com.kamcci.modules.system.construction.tx.advice.SystemConstructionTXAdvice
import com.kamcci.modules.system.construction.tx.pointcut.CustomAnnotationMatchingPointcut
import org.aopalliance.intercept.MethodInterceptor
import org.springframework.aop.Advisor
import org.springframework.aop.Pointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SystemConstructionTxConfig(
    // txMananger의 구현체는 모듈 사용처의 구현체가 주입됨
    private val txManager: PlatformTransactionManager,
    environment: Environment,
) {
    private val txPath = environment.getProperty("system.construction.tx.class-path.transaction").toString()

    @Bean
    @Qualifier("txMethod")
    fun txAnnotationPointcut(): Pointcut {
        return CustomAnnotationMatchingPointcut(getAnnotationClass(txPath).java)
    }

    @Qualifier("txClass")
    @Bean
    fun customTransactionAdvice(): MethodInterceptor {
        val customTxAdvice = SystemConstructionTXAdvice(getAnnotationClass(txPath).java, txManager)
        return customTxAdvice
    }

    @Bean
    fun customTransactionAdvisor(): Advisor {
        return DefaultPointcutAdvisor(txAnnotationPointcut(), customTransactionAdvice())
    }
}