//package com.kamcci.modules.system.construction.tx.pointcut
//
//import com.kamcci.modules.system.construction.sample.NonTxInterfaceImpl
//import com.kamcci.modules.system.construction.sample.TransactionMethodTarget
//import com.kamcci.modules.system.construction.sample.TxInterfaceImpl
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.springframework.aop.MethodMatcher
//import java.lang.reflect.Method
//
//
//class CustomAnnotationMatchingPointcutTest {
//
//    @Test
//    fun `MethodMatcher 조회 - 성공`() {
//        // given
//        val customAnnotationMatchingPointcut = getCustomAnnotationMatchingPointcut()
//
//        // when
//        val methodMatcher = customAnnotationMatchingPointcut.methodMatcher
//
//        // then
//        assertThat(methodMatcher).isInstanceOf(MethodMatcher::class.java)
//    }
//
//    @Test
//    fun `MethodMatcher isRuntime은 항상 false - 성공`() {
//        // given
//        val customAnnotationMatchingPointcut = getCustomAnnotationMatchingPointcut()
//
//        // when
//        val methodMatcher = customAnnotationMatchingPointcut.methodMatcher
//
//        // then
//        assertThat(methodMatcher.isRuntime).isFalse()
//    }
//
//    @Test
//    fun `메서드에 어노테이션 존재 matches - 성공`() {
//        // given
//        val customAnnotationMatchingPointcut = getCustomAnnotationMatchingPointcut()
//        val method: Method = TransactionMethodTarget::class.java.getDeclaredMethod("txMethod")
//        val targetClass: Class<*> = TransactionMethodTarget::class.java
//
//        // when
//        val isMatches = customAnnotationMatchingPointcut.methodMatcher.matches(method, targetClass)
//
//        // then
//        assertThat(isMatches).isTrue()
//    }
//
//    @Test
//    fun `클래스에 어노테이션 존재 matches - 성공`() {
//        // given
//        val customAnnotationMatchingPointcut = getCustomAnnotationMatchingPointcut()
//        val method: Method = TransactionMethodTarget::class.java.getDeclaredMethod("txNotAnnotatedMethod")
//        val targetClass: Class<*> = TransactionMethodTarget::class.java
//
//        // when
//        val isMatches = customAnnotationMatchingPointcut.methodMatcher.matches(method, targetClass)
//
//        // then
//        assertThat(isMatches).isTrue()
//    }
//
//    @Test
//    fun `인터페이스에 어노테이션 존재 matches - 성공`() {
//        // given
//        val customAnnotationMatchingPointcut = getCustomAnnotationMatchingPointcut()
//        val method: Method = TxInterfaceImpl::class.java.getDeclaredMethod("txMethod")
//        val targetClass: Class<*> = TxInterfaceImpl::class.java
//
//
//        // when
//        val isMatches = customAnnotationMatchingPointcut.methodMatcher.matches(method, targetClass)
//
//        // then
//        assertThat(isMatches).isTrue()
//    }
//
//    @Test
//    fun `어노테이션 미존재 matches - 실패`() {
//        // given
//        val customAnnotationMatchingPointcut = getCustomAnnotationMatchingPointcut()
//        val method: Method = NonTxInterfaceImpl::class.java.getDeclaredMethod("txNotAnnotatedMethod")
//        val targetClass: Class<*> = NonTxInterfaceImpl::class.java
//
//        // when
//        val isMatches = customAnnotationMatchingPointcut.methodMatcher.matches(method, targetClass, null)
//
//        // then
//        assertThat(isMatches).isFalse()
//    }
//}