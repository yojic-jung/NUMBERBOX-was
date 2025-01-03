package com.kamcci.modules.system.construction.di.registrar

import com.kamcci.modules.system.construction.MockBeanConfig
import com.kamcci.modules.system.construction.di.processor.BeanDefinitionPropertyProcessor
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [MockBeanConfig::class])
@SpringBootTest
class CustomAnnotationCapableBeanRegistrarTest(
    @Autowired
    private val beanFactory: ConfigurableListableBeanFactory,
) {
    private val beanDefinitionPropertyProcessor: BeanDefinitionPropertyProcessor = Mockito.mock()
    private val customAnnotationCapableBeanFactory =
        CustomAnnotationCapableBeanRegistrar(beanDefinitionPropertyProcessor)

    @Test
    fun `beanDefinition 등록 - 성공`() {
        // given
        val registry = beanFactory as BeanDefinitionRegistry

        // when & then
        assertDoesNotThrow {
            customAnnotationCapableBeanFactory.registerOnlyWith(
                UseCase::class,
                "com.kammci.modules.system.construction.di,com.kammci.modules.system.construction.tx",
                registry
            )
        }
    }

}