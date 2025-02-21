package com.kamcci.modules.system.construction.mock.common

import com.kamcci.modules.system.construction.di.config.CustomBeanAnnotationProperty
import com.kamcci.modules.system.construction.di.processor.BeanDefinitionPropertyProcessor
import org.springframework.beans.factory.support.BeanDefinitionBuilder

class MockBeanDefinitionPropertyProcessor : BeanDefinitionPropertyProcessor {
    override fun <T> modify(
        customAnnotationProperty: CustomBeanAnnotationProperty,
        beanClass: Class<T>,
        beanDefinitionBuilder: BeanDefinitionBuilder
    ) {

    }
}