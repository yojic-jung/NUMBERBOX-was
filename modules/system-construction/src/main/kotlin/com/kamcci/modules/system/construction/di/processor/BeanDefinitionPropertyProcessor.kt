package com.kamcci.modules.system.construction.di.processor

import org.springframework.beans.factory.support.BeanDefinitionBuilder

/**
 * 인스턴스화될 빈의 BeanDeifinition 속성 설정 및 변경 처리기
 */
interface BeanDefinitionPropertyProcessor {
    fun <T> modify(beanClass: Class<T>, beanDefinitionBuilder: BeanDefinitionBuilder)
}
