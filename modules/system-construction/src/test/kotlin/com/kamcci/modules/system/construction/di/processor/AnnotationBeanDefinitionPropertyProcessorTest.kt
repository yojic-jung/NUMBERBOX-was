package com.kamcci.modules.system.construction.di.processor

import com.kamcci.modules.system.construction.sample.CustomHasNotOptionBean
import com.kamcci.modules.system.construction.sample.CustomPrimaryBean
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.support.AutowireCandidateQualifier
import org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition

class AnnotationBeanDefinitionPropertyProcessorTest {
    private val annotationBeanDefinitionModifyProcessor = AnnotationBeanDefinitionPropertyProcessor()

    @Test
    fun `Primary 및 Qualifier 속성 설정 - 성공`() {
        // given
        val primaryAnnotClass = CustomPrimaryBean::class.java
        val beanDefBuilder = genericBeanDefinition()

        // when
        annotationBeanDefinitionModifyProcessor.modify(primaryAnnotClass, beanDefBuilder)

        // then
        assertThat(beanDefBuilder.beanDefinition.isPrimary).isTrue()
        beanDefBuilder.beanDefinition.qualifiers.forEach {
            assertThat(it).isInstanceOf(AutowireCandidateQualifier::class.java)
        }
    }

    @Test
    fun `Primary 및 Qualifier 속성 설정 - 실패`() {
        // given
        val optionHasNotBean = CustomHasNotOptionBean::class.java
        val beanDefBuilder = genericBeanDefinition()

        // when
        annotationBeanDefinitionModifyProcessor.modify(optionHasNotBean, beanDefBuilder)

        // then
        assertThat(beanDefBuilder.beanDefinition.isPrimary).isFalse()
        assertThat(beanDefBuilder.beanDefinition.qualifiers).isEmpty()
    }
}
