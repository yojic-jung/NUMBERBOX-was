package com.kammci.modules.logging.engine.stub.common

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.aspectj.lang.reflect.SourceLocation
import org.aspectj.runtime.internal.AroundClosure

/**
 * ProceedingJoinPoint 스텁
 */
class MockProceedingJoinPoint(private val returnValue: Any?) : ProceedingJoinPoint {
    override fun proceed(): Any? {
        return returnValue
    }

    // 아래 메서드는 미사용

    override fun getArgs(): Array<Any>? {
        return null
    }

    override fun getSignature(): Signature? {
        return null
    }

    override fun getSourceLocation(): SourceLocation? {
        return null
    }

    override fun proceed(p0: Array<out Any>?): Any {
        return ""
    }

    override fun `set$AroundClosure`(p0: AroundClosure?) {
        // 미사용
    }

    override fun `stack$AroundClosure`(arc: AroundClosure?) {
        super.`stack$AroundClosure`(arc)
    }

    override fun getKind(): String {
        return ""
    }

    override fun getStaticPart(): JoinPoint.StaticPart? {
        return null
    }

    override fun toShortString(): String {
        return ""
    }

    override fun toLongString(): String? {
        return null
    }

    override fun getThis(): Any? {
        return null
    }

    override fun getTarget(): Any? {
        return null
    }
}