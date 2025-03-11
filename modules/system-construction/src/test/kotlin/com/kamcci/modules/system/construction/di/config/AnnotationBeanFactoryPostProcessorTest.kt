package com.kamcci.modules.system.construction.di.config

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.support.DefaultListableBeanFactory

class AnnotationBeanFactoryPostProcessorTest {

    @Test
    fun `BeanFactoryPostProcessor 정상 실행 - 성공`() {
        // given
        val beanFactory = DefaultListableBeanFactory()
        val beanFactoryPostProcessor = AnnotationBeanFactoryPostProcessor()

        // when & then
        assertDoesNotThrow {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory)
        }
    }
}
