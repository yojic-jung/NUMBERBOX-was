package com.kamcci.modules.system.construction.di.registrar

import com.kamcci.modules.system.construction.di.processor.BeanDefinitionPropertyProcessor
import com.kamcci.modules.system.construction.dummy.DiTestFixture.getCustomAnnotationProperty
import com.kamcci.modules.system.construction.stub.common.MockBeanDefinitionPropertyProcessor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry

class CustomAnnotationCapableBeanRegistrarTest {
    private val beanDefinitionPropertyProcessor: BeanDefinitionPropertyProcessor = MockBeanDefinitionPropertyProcessor()
    private val customAnnotationCapableBeanFactory =
        CustomAnnotationCapableBeanRegistrar(beanDefinitionPropertyProcessor)

    @Test
    fun `beanDefinition 등록 - 성공`() {
        // given
        val registry: BeanDefinitionRegistry = SimpleBeanDefinitionRegistry()

        // when
        customAnnotationCapableBeanFactory.registerOnlyWith(
            getCustomAnnotationProperty(),
            registry
        )

        // then
        val bean = registry.getBeanDefinition("testBean")
        assertThat(bean).isNotNull
    }
}