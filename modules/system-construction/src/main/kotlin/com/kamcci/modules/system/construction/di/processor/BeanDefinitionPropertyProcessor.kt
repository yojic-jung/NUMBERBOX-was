package com.kamcci.modules.system.construction.di.processor

import com.kamcci.modules.system.construction.di.config.CustomBeanAnnotationProperty
import org.springframework.beans.factory.support.BeanDefinitionBuilder

/**
 * 인스턴스화될 빈의 BeanDeifinition 속성 설정 및 변경 처리기
 */
interface BeanDefinitionPropertyProcessor {
    fun <T> modify(
        customAnnotationProperty: CustomBeanAnnotationProperty,
        beanClass: Class<T>,
        beanDefinitionBuilder: BeanDefinitionBuilder
    )
}
