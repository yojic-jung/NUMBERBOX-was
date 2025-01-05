package com.kamcci.modules.system.construction.di.registrar

import com.kamcci.modules.system.construction.di.processor.BeanDefinitionPropertyProcessor
import com.kamcci.modules.system.construction.dummy.DiTestFixture.getCustomAnnotationProperty
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.atLeast
import org.mockito.kotlin.verify
import org.springframework.beans.factory.support.BeanDefinitionRegistry

class CustomAnnotationCapableBeanRegistrarTest {
    private val beanDefinitionPropertyProcessor: BeanDefinitionPropertyProcessor = mock()
    private val customAnnotationCapableBeanFactory =
        CustomAnnotationCapableBeanRegistrar(beanDefinitionPropertyProcessor)

    @Test
    fun `beanDefinition 등록 - 성공`() {
        // given
        val registry: BeanDefinitionRegistry = mock()

        // when
        customAnnotationCapableBeanFactory.registerOnlyWith(
            getCustomAnnotationProperty(),
            registry
        )

        // then
        verify(registry, atLeast(1)).registerBeanDefinition(any(), any())
    }
}