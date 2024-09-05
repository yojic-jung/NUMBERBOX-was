package com.hexagonal.systemconstructiondi.factory

import org.springframework.beans.factory.support.BeanDefinitionRegistry
import kotlin.reflect.KClass

/**
 * basePackages에 존재하는 customBeanAnnotation이 붙은 클래스를 스프링 빈으로 등록함
 */
interface AnnotationCapableBeanFactory {
    fun registerOnlyWith(
        customBeanAnnotation: KClass<out Annotation>,
        basePackages: String,
        registry: BeanDefinitionRegistry,
    )
}
