package com.kamcci.modules.system.construction.dummy

import com.kamcci.modules.system.construction.common.util.FindAnnotation.getAnnotationClass
import com.kamcci.modules.system.construction.dummy.TestConstant.SUCCESS
import com.kamcci.modules.system.construction.tx.pointcut.CustomAnnotationMatchingPointcut


object TestConstant {
    const val SUCCESS = "SUCCESS"
}

@TXExecute
@CustomBean
class TransactionMethodTarget {
    @TXExecute
    fun txMethod() = SUCCESS

    fun txNotAnnotatedMethod() = SUCCESS
}

class NonTxClass {
    fun txNotAnnotatedMethod() = SUCCESS
}

@TXExecute
interface TxInterface {
    @TXExecute
    fun txMethod(): String

    fun txNotAnnotatedMethod(): String
}

class TxInterfaceImpl : TxInterface {
    override fun txMethod() = SUCCESS
    override fun txNotAnnotatedMethod() = SUCCESS
}


interface NonTxInterface {
    fun txMethod(): String

    fun txNotAnnotatedMethod(): String
}

class NonTxInterfaceImpl : NonTxInterface {
    override fun txMethod() = SUCCESS
    override fun txNotAnnotatedMethod() = SUCCESS
    fun nonInterfaceMethod() = SUCCESS
}

object TxFixture {
    fun getCustomAnnotationMatchingPointcut() =
        CustomAnnotationMatchingPointcut(
            getAnnotationClass("com.kamcci.modules.system.construction.dummy.TXExecute").java
        )
}