package com.kamcci.modules.system.construction.di.resolver

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.beans.factory.support.SimpleAutowireCandidateResolver

class CustomQualifierAnnotationRegistrarTest {
    private val customQualifierAnnotationRegistrar = CustomQualifierAnnotationRegistrar()

    @Test
    fun `Qualifier 역할 진행할 커스텀 어노테이션 설정 - 성공`() {
        // given
        val beanFactory = DefaultListableBeanFactory()
        val qualifierResolver: QualifierAnnotationAutowireCandidateResolver = mock()
        beanFactory.autowireCandidateResolver = qualifierResolver
        val customAnnot = CustomQualifier::class

        // when
        customQualifierAnnotationRegistrar.add(customAnnot, beanFactory)

        // then
        verify(qualifierResolver).addQualifierType(Qualifier::class.java)
        verify(qualifierResolver).addQualifierType(customAnnot.java)
    }

    @Test
    fun `Qualifier 역할 진행할 커스텀 어노테이션 설정 - 실패`() {
        // given
        val beanFactory = DefaultListableBeanFactory()
        val qualifierResolver = SimpleAutowireCandidateResolver()
        beanFactory.autowireCandidateResolver = qualifierResolver
        val customAnnot = CustomQualifier::class

        // when
        customQualifierAnnotationRegistrar.add(customAnnot, beanFactory)

        // then
        assertThat(qualifierResolver !is QualifierAnnotationAutowireCandidateResolver).isTrue()
    }
}

annotation class CustomQualifier(val value: String)