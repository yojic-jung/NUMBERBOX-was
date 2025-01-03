package com.kamcci.modules.system.construction.di.config

import com.kamcci.modules.system.construction.MockBeanConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [MockBeanConfig::class])
@SpringBootTest
class AnnotationBeanFactoryPostProcessorTest @Autowired constructor(
    // auto config의 ConfigurableListableBeanFactory 구현체
    private val beanFactory: ConfigurableListableBeanFactory,
) {
    // 테스트 대상
    private val annotationBeanFactoryPostProcessor = AnnotationBeanFactoryPostProcessor()

    @Test
    fun `ConfigurableListableBeanFactory 구현체 주입 - 성공`() {
        // when & then
        /**
         * 스프링 autoConfig의 ConfigurableListableBeanFactory 구현체가
         * BeanDefinitionRegistry, DefaultListableBeanFactory 이어야함
         *
         * 스프링 의존설정에 따라 ConfigurableListableBeanFactory의 구현체가 달라질 수 있음
         * 모듈 사용처의 구현체가 중요함
         */
        assertDoesNotThrow {
            annotationBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory)
        }
    }
}