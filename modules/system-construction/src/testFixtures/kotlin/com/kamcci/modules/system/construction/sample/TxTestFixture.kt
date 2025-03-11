package com.kamcci.modules.system.construction.sample

import com.kamcci.modules.system.construction.sample.TestConstant.SUCCESS
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import com.kamcci.numberbox.app.domain.system.construction.UseCase


object TestConstant {
    const val SUCCESS = "SUCCESS"
}

@TXExecute
@UseCase
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
