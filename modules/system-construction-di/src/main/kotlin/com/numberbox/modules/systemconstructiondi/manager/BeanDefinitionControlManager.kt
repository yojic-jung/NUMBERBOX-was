package com.hexagonal.systemconstructiondi.manager

import org.springframework.beans.factory.support.BeanDefinitionBuilder

/**
 * 인스턴스화될 빈의 BeanDeifinition 조작을 위임하는 매니저
 */
interface BeanDefinitionControlManager {
    fun <T> delegate(beanClass: Class<T>, beanDefinitionBuilder: BeanDefinitionBuilder)
}
