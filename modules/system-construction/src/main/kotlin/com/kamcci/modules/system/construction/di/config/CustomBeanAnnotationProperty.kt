package com.kamcci.modules.system.construction.di.config

import com.kamcci.modules.system.construction.common.util.FindAnnotation.getAnnotationClass

/**
 * 스프링 빈 속성을 사용할 커스텀 어노테이션 클래스 경로
 */
data class CustomBeanAnnotationProperty(
    // DI 기능을 적용할 annotation 경로
    val customBean: String,
    // customBean 어노테이션이 적용된 클래스들의 경로
    val basePackage: String,
    // 빈 스코프
    val beanScope: String,
    // primary 기능을 적용할 annotation 경로
    val primary: String,
    // qualifier 기능을 적용할 annotation 경로
    val qualifier: String,
) {
    fun getCustomBeanAnnotation() = getAnnotationClass(customBean)
    fun getPrimaryAnnotation() = getAnnotationClass(primary)
    fun getQualifierAnnotation() = getAnnotationClass(qualifier)
}