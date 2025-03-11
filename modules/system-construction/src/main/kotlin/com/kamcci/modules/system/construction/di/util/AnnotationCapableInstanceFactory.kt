package com.kamcci.modules.system.construction.di.util

import com.kamcci.modules.system.construction.di.processor.AnnotationBeanDefinitionPropertyProcessor
import com.kamcci.modules.system.construction.di.registrar.AnnotationCapableBeanRegistrar
import com.kamcci.modules.system.construction.di.registrar.CustomAnnotationCapableBeanRegistrar
import com.kamcci.modules.system.construction.di.resolver.CustomQualifierAnnotationResolver
import com.kamcci.modules.system.construction.di.resolver.QualifierAnnotationResolver

/**
 * system-construction-di 모듈의 객체 생성 팩토리
 * BeanFactoryPostProcessor는 빈 생성 이전에 실행되기에 스프링의 DI를 사용할 수 없음
 * 따라서 직접 의존관계 설정하여 객체 주입시켜줄 팩토리 필요함
 */
object AnnotationCapableInstanceFactory {
    fun getAnnotationCapableBeanFactory(): AnnotationCapableBeanRegistrar {
        val beanDefinitionModifyByAnnotManager =
            AnnotationBeanDefinitionPropertyProcessor()
        return CustomAnnotationCapableBeanRegistrar(beanDefinitionModifyByAnnotManager)
    }

    fun getDICapableAnnotationResolver(): QualifierAnnotationResolver {
        return CustomQualifierAnnotationResolver()
    }

}
