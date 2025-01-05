package com.kamcci.modules.system.construction.di.registrar

import com.kamcci.modules.system.construction.di.config.CustomBeanAnnotationProperty
import org.springframework.beans.factory.support.BeanDefinitionRegistry

/**
 * basePackages에 존재하는 customBeanAnnotation이 붙은 클래스를 스프링 빈으로 등록함
 */
interface AnnotationCapableBeanRegistrar {
    fun registerOnlyWith(
        customAnnotationProperty: CustomBeanAnnotationProperty,
        registry: BeanDefinitionRegistry,
    )
}
