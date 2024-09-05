package com.hexagonal.systemconstructiondi.manager


import com.hexagonal.systemconstructiondi.resolver.BeanDefinitionResolver
import com.hexagonal.systemconstructiondi.config.CustomAnnotationBeanConfigHolder.Companion.BEAN_SCOPE
import com.hexagonal.systemconstructiondi.config.CustomAnnotationBeanConfigHolder.Companion.CUSTOM_PRIMARY_ANNOTATION
import com.hexagonal.systemconstructiondi.config.CustomAnnotationBeanConfigHolder.Companion.CUSTOM_QUALIFIER_ANNOTATION
import org.springframework.beans.factory.support.BeanDefinitionBuilder

/**
 * Priority는 Primary로, Aliases는 Qualifer로 기준을 잡고 
 * BeanDefintion 변경 작업을 resolver에게 위임
 */
class AnnotationBeanDefinitionModifyManager(
    private val beanDefinitionResolver: BeanDefinitionResolver,
) : BeanDefinitionControlManager {
    override fun <T> delegate(beanClass: Class<T>, beanDefinitionBuilder: BeanDefinitionBuilder) {
        // scope 설정
        beanDefinitionBuilder.setScope(BEAN_SCOPE)

        // primary 설정
        if (beanClass.isAnnotationPresent(CUSTOM_PRIMARY_ANNOTATION.java)) {
            beanDefinitionResolver.modifyPrimary(true, beanDefinitionBuilder)
        } // qualifier 설정
        else if (beanClass.isAnnotationPresent(CUSTOM_QUALIFIER_ANNOTATION.java)) {
            val aliasesAnnotation = beanClass.getAnnotation(CUSTOM_QUALIFIER_ANNOTATION.java)
            val aliases = aliasesAnnotation.value.split(",").map { it.trim() }
            beanDefinitionResolver.addQualifier(aliases, beanDefinitionBuilder)
        }
    }
}
