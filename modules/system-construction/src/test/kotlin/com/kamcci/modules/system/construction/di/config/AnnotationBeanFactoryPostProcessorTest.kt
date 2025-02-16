package com.kamcci.modules.system.construction.di.config

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.mock.env.MockEnvironment

class AnnotationBeanFactoryPostProcessorTest {

    @Test
    fun `BeanFactoryPostProcessor 정상 실행 - 성공`() {
        // given
        val beanFactory = MockDefaultListableBeanFactory()
        val beanFactoryPostProcessor = AnnotationBeanFactoryPostProcessor()

        // when & then
        assertDoesNotThrow {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory)
        }
    }
}

// AnnotationBeanFactoryPostProcessor 클래스의 postProcessBeanFactory에 인자값으로 들어갈 스텁
class MockDefaultListableBeanFactory : DefaultListableBeanFactory() {
    override fun <T : Any?> getBean(requiredType: Class<T>): T & Any {
        val environment = MockEnvironment()
        environment.setProperty(
            "system.construction.di.class-path.customBean",
            "com.kamcci.modules.system.construction.dummy.CustomBean"
        )
        environment.setProperty(
            "system.construction.di.class-path.basePackage",
            "com.kamcci.modules.system.construction.dummy"
        )
        environment.setProperty("system.construction.di.class-path.beanScope", "")
        environment.setProperty(
            "system.construction.di.class-path.primary",
            "com.kamcci.modules.system.construction.dummy.CustomPrimary"
        )
        environment.setProperty(
            "system.construction.di.class-path.qualifier",
            "com.kamcci.modules.system.construction.dummy.CustomQualifier"
        )
        return environment as (T & Any)
    }
}