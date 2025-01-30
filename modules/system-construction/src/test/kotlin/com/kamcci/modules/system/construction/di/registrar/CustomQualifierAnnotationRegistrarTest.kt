package com.kamcci.modules.system.construction.di.registrar

import com.kamcci.modules.system.construction.dummy.CustomQualifier
import com.kamcci.modules.system.construction.stub.common.MockQualifierAnnotationAutowireCandidateResolver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.beans.factory.support.SimpleAutowireCandidateResolver

class CustomQualifierAnnotationRegistrarTest {
    private val customQualifierAnnotationRegistrar = CustomQualifierAnnotationRegistrar()

    @Test
    fun `Qualifier 역할 진행할 커스텀 어노테이션 설정 - 성공`() {
        // given
        val beanFactory = DefaultListableBeanFactory()
        val qualifierResolver = MockQualifierAnnotationAutowireCandidateResolver()
        beanFactory.autowireCandidateResolver = qualifierResolver
        val customAnnot = CustomQualifier::class

        // when
        customQualifierAnnotationRegistrar.add(customAnnot, beanFactory)

        // then
        val resolver = beanFactory.autowireCandidateResolver
        assertThat(resolver).isInstanceOf(QualifierAnnotationAutowireCandidateResolver::class.java)
        resolver as MockQualifierAnnotationAutowireCandidateResolver
        assertThat(resolver.hasQualifier(customAnnot.java)).isTrue()
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
        assertThat(qualifierResolver).isNotInstanceOf(QualifierAnnotationAutowireCandidateResolver::class.java)
    }
}

