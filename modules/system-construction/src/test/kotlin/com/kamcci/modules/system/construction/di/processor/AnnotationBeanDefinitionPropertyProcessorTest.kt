package com.kamcci.modules.system.construction.di.processor

import com.kamcci.modules.system.construction.dummy.DiTestFixture.getCustomAnnotationProperty
import com.kamcci.modules.system.construction.dummy.TestBean
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.GenericBeanDefinition

class AnnotationBeanDefinitionPropertyProcessorTest {
    private val annotationBeanDefinitionModifyProcessor = AnnotationBeanDefinitionPropertyProcessor()

    @Test
    fun `Primary 및 Qualifier 속성 설정 - 성공`() {
        // given
        val beanDefBuilder = mock(BeanDefinitionBuilder::class.java)
        `when`(beanDefBuilder.beanDefinition).thenReturn(GenericBeanDefinition())

        // when
        annotationBeanDefinitionModifyProcessor.modify(
            getCustomAnnotationProperty(),
            TestBean::class.java,
            beanDefBuilder
        )

        // then
        verify(beanDefBuilder).setPrimary(true)
        verify(beanDefBuilder).beanDefinition
    }

    @Test
    fun `Primary 및 Qualifier 속성 설정 - 실패`() {
        // given
        val beanDefBuilder = mock(BeanDefinitionBuilder::class.java)

        // when
        annotationBeanDefinitionModifyProcessor.modify(
            getCustomAnnotationProperty(),
            Any::class.java,
            beanDefBuilder
        )

        // then
        verify(beanDefBuilder, never()).setPrimary(true)
        verify(beanDefBuilder, never()).beanDefinition
    }
}
