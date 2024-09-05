package com.hexagonal.systemconstructiondi.resolver

import org.springframework.beans.factory.support.BeanDefinitionBuilder

/**
 * primary 및 qualifier 속성 변경 resolver
 */
interface BeanDefinitionResolver {
    fun modifyPrimary(isPrimary: Boolean, beanDefinitionBuilder: BeanDefinitionBuilder)

    fun addQualifier(qualifierValues: List<String>, beanDefinitionBuilder: BeanDefinitionBuilder)
}
