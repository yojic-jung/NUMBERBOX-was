package com.kamcci.modules.system.construction.common.util

import com.kamcci.modules.system.construction.common.util.FindAnnotation.findInterfaceAnnotation
import com.kamcci.modules.system.construction.sample.NonTxInterfaceImpl
import com.kamcci.modules.system.construction.sample.TxInterfaceImpl
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod

class FindAnnotationTest {

    @Test
    fun `인터페이스 메서드에 부착된 어노테이션 찾기 - 성공`() {
        // given
        val txTargetMethod =
            TxInterfaceImpl::class.declaredFunctions.find { it.name == "txMethod" }?.javaMethod

        // when
        val annotation = findInterfaceAnnotation(TXExecute::class.java, txTargetMethod!!)

        // then
        assertThat(annotation).isNotNull
    }

    @Test
    fun `인터페이스에 부착된 어노테이션 찾기 - 성공`() {
        // given
        val txTargetMethod =
            TxInterfaceImpl::class.declaredFunctions.find { it.name == "txNotAnnotatedMethod" }?.javaMethod

        // when
        val annotation = findInterfaceAnnotation(TXExecute::class.java, txTargetMethod!!)

        // then
        assertThat(annotation).isNotNull
    }

    @Test
    fun `인터페이스에 부착된 어노테이션 없는 경우 - 성공`() {
        // given
        val txTargetMethod =
            NonTxInterfaceImpl::class.declaredFunctions.find { it.name == "txNotAnnotatedMethod" }?.javaMethod

        // when
        val annotation = findInterfaceAnnotation(TXExecute::class.java, txTargetMethod!!)

        // then
        assertThat(annotation).isNull()
    }

    @Test
    fun `인터페이스에 부착된 어노테이션 찾기 - 실패(잘못된 메서드명)`() {
        // given
        val txTargetMethod =
            NonTxInterfaceImpl::class.declaredFunctions.find { it.name == "nonInterfaceMethod" }?.javaMethod

        // when
        val annotation = findInterfaceAnnotation(TXExecute::class.java, txTargetMethod!!)

        // then
        assertThat(annotation).isNull()
    }
}