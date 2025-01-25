package com.kamcci.modules.system.construction.tx.advice

import com.kamcci.modules.system.construction.common.util.FindAnnotation.findInterfaceAnnotation
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

/**
 * Def. 스프링에서 트랜잭션 처리에 사용되는 프록시
 * Desc.
 * - 타깃 객체를 참조속성이 아닌 메서드 인자값으로 전달해주어 스프링 빈으로 등록 가능
 * - 메서드 선정 알고리즘(포인트컷)에 의존하지 않음, 오직 부가기능 제공만
 */
class SystemConstructionTXAdvice(
    private val customTxAnnotation: Class<out Annotation>,
    private val transactionManager: PlatformTransactionManager,
) : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? {
        // 타킷 메서드에 적용된 어노테이션 추출
        val method = invocation.method
        val customTXAnnotation = method.getAnnotation(customTxAnnotation)

        // 타킷 클래스에 적용된 어노테이션 추출
        val targetClass = method.declaringClass
        val classAnnotation = targetClass.getAnnotation(customTxAnnotation)

        // 인터페이스에 적용된 어노테이션 추출
        val interfaceAnnotation = findInterfaceAnnotation(customTxAnnotation, method)

        // 어노테이션은 메서드 -> 클래스 순으로 우선순위
        val txAnnotation =
            when {
                customTXAnnotation != null -> customTXAnnotation
                classAnnotation != null -> classAnnotation
                interfaceAnnotation != null -> interfaceAnnotation
                else -> {
                    return invocation.proceed()
                }
            }

        // 어노테이션에 적용된 트랜잭션 속성 객체 생성
        val txDefinition = DefaultTransactionDefinition()
        val isolationLevel = txAnnotation::class.java.getDeclaredMethod("isolation").invoke(txAnnotation)
        val propagationBehavior = txAnnotation::class.java.getDeclaredMethod("propagation").invoke(txAnnotation)
        val readOnly = txAnnotation::class.java.getDeclaredMethod("readOnly").invoke(txAnnotation)
        txDefinition.isolationLevel = isolationLevel as Int
        txDefinition.propagationBehavior = propagationBehavior as Int
        txDefinition.isReadOnly = readOnly as Boolean
        val txStatus = transactionManager.getTransaction(txDefinition)

        try {
            val returnVal = invocation.proceed()
            transactionManager.commit(txStatus)
            return returnVal
        } catch (e: RuntimeException) {
            transactionManager.rollback(txStatus)
            throw e
        } catch (e: Exception) {
            transactionManager.rollback(txStatus)
            throw e
        } catch (e: Throwable) {
            transactionManager.rollback(txStatus)
            throw e
        }
    }
}
