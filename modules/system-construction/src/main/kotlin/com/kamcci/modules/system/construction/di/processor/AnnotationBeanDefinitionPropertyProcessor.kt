package com.kamcci.modules.system.construction.di.processor


import com.kamcci.modules.system.construction.di.config.CustomDIAnnotationBeanConstConfig.BEAN_SCOPE
import com.kamcci.modules.system.construction.di.config.CustomDIAnnotationBeanConstConfig.CUSTOM_PRIMARY_ANNOTATION
import com.kamcci.modules.system.construction.di.config.CustomDIAnnotationBeanConstConfig.CUSTOM_QUALIFIER_ANNOTATION
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.support.AutowireCandidateQualifier
import org.springframework.beans.factory.support.BeanDefinitionBuilder

/**
 * Priority는 Primary로, Aliases는 Qualifer로 기준을 잡고
 * BeanDefintion 변경 작업 진행
 */
class AnnotationBeanDefinitionPropertyProcessor : BeanDefinitionPropertyProcessor {
    override fun <T> modify(beanClass: Class<T>, beanDefinitionBuilder: BeanDefinitionBuilder) {
        // scope 설정
        beanDefinitionBuilder.setScope(BEAN_SCOPE)

        // primary 설정
        if (beanClass.isAnnotationPresent(CUSTOM_PRIMARY_ANNOTATION.java)) {
            beanDefinitionBuilder.setPrimary(true)
        }

        // qualifier 설정
        if (beanClass.isAnnotationPresent(CUSTOM_QUALIFIER_ANNOTATION.java)) {
            val aliasesAnnotation = beanClass.getAnnotation(CUSTOM_QUALIFIER_ANNOTATION.java)
            val aliases = aliasesAnnotation.value.split(",").map { it.trim() }
            aliases.forEach { alias ->
                val qualifier = AutowireCandidateQualifier(Qualifier::class.java)
                qualifier.setAttribute("value", alias)
                beanDefinitionBuilder.beanDefinition.addQualifier(qualifier)
            }
        }
    }
}
