package com.kamcci.modules.system.construction.di.registrar

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import kotlin.reflect.KClass

class CustomQualifierAnnotationRegistrar : QualifierAnnotationRegistrar {
    // 의존주입시 customAnnot도 qualifier 처럼 사용될 수 있게 Qualifier 타입에  customAnnot도 타입 추가
    override fun add(customAnnot: KClass<out Annotation>, beanFactory: DefaultListableBeanFactory) {
        val qualifierResolver = beanFactory.autowireCandidateResolver
        if (qualifierResolver is QualifierAnnotationAutowireCandidateResolver) {
            qualifierResolver.addQualifierType(Qualifier::class.java)
            qualifierResolver.addQualifierType(customAnnot.java)
        }
    }
}
