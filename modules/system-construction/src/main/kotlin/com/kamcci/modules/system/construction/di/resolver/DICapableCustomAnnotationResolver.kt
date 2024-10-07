package com.kamcci.modules.system.construction.di.resolver

import com.kamcci.modules.system.construction.di.config.CustomDIAnnotationBeanConstConfig.CUSTOM_QUALIFIER_ANNOTATION
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import kotlin.reflect.KClass

class DICapableCustomAnnotationResolver : DICapableAnnotationResolver {
    // 의존주입시 aliases도 qualifier처럼 사용될 수 있게 Qualifier 타입에 CUSTOM_QUALIFIER_ANNOTATION 타입 추가
    override fun add(annotation: KClass<out Annotation>, beanFactory: DefaultListableBeanFactory) {
        val qualifierResolver = beanFactory.autowireCandidateResolver
        if (qualifierResolver is QualifierAnnotationAutowireCandidateResolver) {
            qualifierResolver.addQualifierType(Qualifier::class.java)
            qualifierResolver.addQualifierType(CUSTOM_QUALIFIER_ANNOTATION.java)
        }
    }
}
