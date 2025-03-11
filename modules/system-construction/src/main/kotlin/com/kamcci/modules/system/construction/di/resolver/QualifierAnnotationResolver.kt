package com.kamcci.modules.system.construction.di.resolver

import org.springframework.beans.factory.support.DefaultListableBeanFactory
import kotlin.reflect.KClass

/**
 * Qualifier역할을 할 수 있는 커스텀 어노테이션 설정
 * annotation은 반드시 value 속성을 가지고 있어야함
 */
interface QualifierAnnotationResolver {
    fun add(customAnnot: KClass<out Annotation>, beanFactory: DefaultListableBeanFactory)
}
