package com.kamcci.modules.system.construction.di.processor

import com.kamcci.modules.system.construction.dummy.DiTestFixture.getCustomAnnotationProperty
import com.kamcci.modules.system.construction.dummy.NonAnnotatedClass
import com.kamcci.modules.system.construction.dummy.TestBean
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.support.AutowireCandidateQualifier
import org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition

class AnnotationBeanDefinitionPropertyProcessorTest {
    private val annotationBeanDefinitionModifyProcessor = AnnotationBeanDefinitionPropertyProcessor()

    @Test
    fun `Primary 및 Qualifier 속성 설정 - 성공`() {
        // given
        val beanDefBuilder = genericBeanDefinition()

        // when
        annotationBeanDefinitionModifyProcessor.modify(
            getCustomAnnotationProperty(),
            TestBean::class.java,
            beanDefBuilder
        )

        // then
        assertThat(beanDefBuilder.beanDefinition.isPrimary).isTrue()
        beanDefBuilder.beanDefinition.qualifiers.forEach {
            assertThat(it).isInstanceOf(AutowireCandidateQualifier::class.java)
        }
    }

    @Test
    fun `Primary 및 Qualifier 속성 설정 - 실패`() {
        // given
        val beanDefBuilder = genericBeanDefinition()

        // when
        annotationBeanDefinitionModifyProcessor.modify(
            getCustomAnnotationProperty(),
            NonAnnotatedClass::class.java,
            beanDefBuilder
        )

        // then
        assertThat(beanDefBuilder.beanDefinition.isPrimary).isFalse()
        assertThat(beanDefBuilder.beanDefinition.qualifiers).isEmpty()
    }
}
