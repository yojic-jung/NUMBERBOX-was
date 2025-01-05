package com.kamcci.modules.system.construction.di.processor


import com.kamcci.modules.system.construction.di.config.CustomBeanAnnotationProperty
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.support.AutowireCandidateQualifier
import org.springframework.beans.factory.support.BeanDefinitionBuilder

/**
 * Priority는 Primary로, Aliases는 Qualifer로 기준을 잡고
 * BeanDefintion 변경 작업 진행
 */
class AnnotationBeanDefinitionPropertyProcessor : BeanDefinitionPropertyProcessor {
    override fun <T> modify(
        customAnnotationProperty: CustomBeanAnnotationProperty,
        beanClass: Class<T>,
        beanDefinitionBuilder: BeanDefinitionBuilder
    ) {
        // scope 설정
        beanDefinitionBuilder.setScope(customAnnotationProperty.beanScope)

        // primary 설정
        if (beanClass.isAnnotationPresent(customAnnotationProperty.getPrimaryAnnotation().java)) {
            beanDefinitionBuilder.setPrimary(true)
        }

        // qualifier 설정
        if (beanClass.isAnnotationPresent(customAnnotationProperty.getQualifierAnnotation().java)) {
            val annotationInstance = beanClass.getAnnotation(customAnnotationProperty.getQualifierAnnotation().java)
            val valueMethod = customAnnotationProperty.getQualifierAnnotation().java.getDeclaredMethod("value")
            val aliases = valueMethod.invoke(annotationInstance) as String // 어노테이션의 value 값
            aliases.split(",").map { it.trim() }.forEach { alias ->
                val qualifier = AutowireCandidateQualifier(Qualifier::class.java)
                qualifier.setAttribute("value", alias)
                beanDefinitionBuilder.beanDefinition.addQualifier(qualifier)
            }
        }
    }
}
