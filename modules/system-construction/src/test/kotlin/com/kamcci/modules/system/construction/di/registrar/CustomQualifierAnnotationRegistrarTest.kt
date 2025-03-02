package com.kamcci.modules.system.construction.di.registrar

import com.kamcci.modules.system.construction.mock.common.MockQualifierAnnotationAutowireCandidateResolver
import com.kamcci.modules.system.construction.sample.CustomQualifier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.beans.factory.support.SimpleAutowireCandidateResolver

class CustomQualifierAnnotationRegistrarTest {
    private val beanFactory = DefaultListableBeanFactory()
    private val customAnnot = CustomQualifier::class

    // 테스트 대상
    private val customQualifierAnnotationRegistrar = CustomQualifierAnnotationRegistrar()

    @Test
    fun `Qualifier 역할 진행할 커스텀 어노테이션 설정 - 성공`() {
        // given
        val qualifierResolver = MockQualifierAnnotationAutowireCandidateResolver()
        beanFactory.autowireCandidateResolver = qualifierResolver

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
        val qualifierResolver = SimpleAutowireCandidateResolver()
        beanFactory.autowireCandidateResolver = qualifierResolver

        // when
        customQualifierAnnotationRegistrar.add(customAnnot, beanFactory)

        // then
        assertThat(qualifierResolver).isNotInstanceOf(QualifierAnnotationAutowireCandidateResolver::class.java)
    }
}

