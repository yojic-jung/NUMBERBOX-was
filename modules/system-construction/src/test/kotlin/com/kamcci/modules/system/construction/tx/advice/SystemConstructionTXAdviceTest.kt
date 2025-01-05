package com.kamcci.modules.system.construction.tx.advice

import com.kamcci.modules.system.construction.dummy.NonTxClass
import com.kamcci.modules.system.construction.dummy.TXExecute
import com.kamcci.modules.system.construction.dummy.TestConstant.SUCCESS
import com.kamcci.modules.system.construction.dummy.TransactionMethodTarget
import com.kamcci.modules.system.construction.dummy.TxInterfaceImpl
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionStatus
import java.lang.reflect.AccessibleObject
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod

class SystemConstructionTXAdviceTest {
    private val transactionManager: PlatformTransactionManager = mock()
    private val systemConstructionTXAdvice: SystemConstructionTXAdvice =
        SystemConstructionTXAdvice(TXExecute::class.java, transactionManager)

    @Test
    fun `트랜잭션 부가기능 처리 - 성공`() {
        // given
        val txTarget = TransactionMethodTarget()
        val txTargetMethods =
            listOf(
                TransactionMethodTarget::class.declaredFunctions.find { it.name == "txMethod" }?.javaMethod,
                TransactionMethodTarget::class.declaredFunctions.find { it.name == "txNotAnnotatedMethod" }?.javaMethod,
            )
        for (txTargetMethod in txTargetMethods) {
            val invocation = object : MethodInvocation {
                override fun getArguments() = emptyArray<Any>()
                override fun getMethod() = txTargetMethod!!
                override fun getThis() = txTarget
                override fun proceed() = txTargetMethod?.invoke(txTarget)
                override fun getStaticPart(): AccessibleObject = txTargetMethod!!
            }

            val txStatus: DefaultTransactionStatus = mock()
            `when`(transactionManager.getTransaction(any())).thenReturn(txStatus)

            // when
            val returnVal = systemConstructionTXAdvice.invoke(invocation)

            // then
            assertThat(returnVal).isEqualTo(SUCCESS)
            verify(transactionManager).commit(txStatus)
        }
    }

    @Test
    fun `트랜잭션 부가기능 처리(인터페이스 대상) - 성공`() {
        // given
        val txTarget = TxInterfaceImpl()
        val txTargetMethod = TxInterfaceImpl::class.declaredFunctions.find { it.name == "txMethod" }?.javaMethod
        val invocation = object : MethodInvocation {
            override fun getArguments() = emptyArray<Any>()
            override fun getMethod() = txTargetMethod!!
            override fun getThis() = txTarget
            override fun proceed() = txTargetMethod?.invoke(txTarget)
            override fun getStaticPart(): AccessibleObject = txTargetMethod!!
        }
        val txStatus: DefaultTransactionStatus = mock()
        `when`(transactionManager.getTransaction(any())).thenReturn(txStatus)

        // when
        val returnVal = systemConstructionTXAdvice.invoke(invocation)

        // then
        assertThat(returnVal).isEqualTo(SUCCESS)
        verify(transactionManager).commit(txStatus)
    }

    @Test
    fun `트랜잭션 부가기능 처리(RuntimeException) - 실패`() {
        // given
        val txTarget = TransactionMethodTarget()
        val txTargetMethod =
            TransactionMethodTarget::class.declaredFunctions.find { it.name == "txMethod" }?.javaMethod

        val invocation = object : MethodInvocation {
            override fun getArguments() = emptyArray<Any>()
            override fun getMethod() = txTargetMethod!!
            override fun getThis() = txTarget
            override fun proceed(): Any {
                throw RuntimeException()
            }

            override fun getStaticPart(): AccessibleObject = txTargetMethod!!
        }

        val txStatus: DefaultTransactionStatus = mock()
        `when`(transactionManager.getTransaction(any())).thenReturn(txStatus)

        // when & then
        assertThrows<RuntimeException> {
            systemConstructionTXAdvice.invoke(invocation)
        }
    }

    @Test
    fun `트랜잭션 부가기능 처리(Exception) - 실패`() {
        // given
        val txTarget = TransactionMethodTarget()
        val txTargetMethod =
            TransactionMethodTarget::class.declaredFunctions.find { it.name == "txMethod" }?.javaMethod

        val invocation = object : MethodInvocation {
            override fun getArguments() = emptyArray<Any>()
            override fun getMethod() = txTargetMethod!!
            override fun getThis() = txTarget
            override fun proceed(): Any {
                throw Exception()
            }

            override fun getStaticPart(): AccessibleObject = txTargetMethod!!
        }

        val txStatus: DefaultTransactionStatus = mock()
        `when`(transactionManager.getTransaction(any())).thenReturn(txStatus)

        // when & then
        assertThrows<Exception> {
            systemConstructionTXAdvice.invoke(invocation)
        }
    }

    @Test
    fun `트랜잭션 부가기능 처리(Throwable) - 실패`() {
        // given
        val txTarget = TransactionMethodTarget()
        val txTargetMethod =
            TransactionMethodTarget::class.declaredFunctions.find { it.name == "txMethod" }?.javaMethod

        val invocation = object : MethodInvocation {
            override fun getArguments() = emptyArray<Any>()
            override fun getMethod() = txTargetMethod!!
            override fun getThis() = txTarget
            override fun proceed(): Any {
                throw Throwable()
            }

            override fun getStaticPart(): AccessibleObject = txTargetMethod!!
        }

        val txStatus: DefaultTransactionStatus = mock()
        `when`(transactionManager.getTransaction(any())).thenReturn(txStatus)

        // when & then
        assertThrows<Throwable> {
            systemConstructionTXAdvice.invoke(invocation)
        }
    }

    @Test
    fun `트랜잭션 부가기능 처리(어노테이션 미존재) - 성공`() {
        // given
        val txTarget = NonTxClass()
        val txTargetMethod =
            NonTxClass::class.declaredFunctions.find { it.name == "txNotAnnotatedMethod" }?.javaMethod
        val invocation = object : MethodInvocation {
            override fun getArguments() = emptyArray<Any>()
            override fun getMethod() = txTargetMethod!!
            override fun getThis() = txTarget
            override fun proceed() = txTargetMethod?.invoke(txTarget)
            override fun getStaticPart(): AccessibleObject = txTargetMethod!!
        }
        val txStatus: DefaultTransactionStatus = mock()
        `when`(transactionManager.getTransaction(any())).thenReturn(txStatus)

        // when
        val returnVal = systemConstructionTXAdvice.invoke(invocation)

        // then
        assertThat(returnVal).isEqualTo(SUCCESS)
        verify(transactionManager, never()).commit(any())
    }
}
