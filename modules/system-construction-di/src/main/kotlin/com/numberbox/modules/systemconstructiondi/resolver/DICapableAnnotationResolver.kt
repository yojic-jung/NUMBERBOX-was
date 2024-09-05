package com.hexagonal.systemconstructiondi.resolver

import org.springframework.beans.factory.support.DefaultListableBeanFactory
import kotlin.reflect.KClass

/**
 * 의존관계(참조속성)에 있는 객체 주입 대상을 별칭을 통해 정할 수 있는 커스텀 어노테이션 설정
 * annotation은 반드시 value 속성을 가지고 있어야함
 */
interface DICapableAnnotationResolver {
    fun add(annotation: KClass<out Annotation>, beanFactory: DefaultListableBeanFactory)
}
