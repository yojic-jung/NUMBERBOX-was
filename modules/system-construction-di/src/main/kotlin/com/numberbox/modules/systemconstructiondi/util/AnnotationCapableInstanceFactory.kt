package com.hexagonal.systemconstructiondi.util

import com.hexagonal.systemconstructiondi.manager.AnnotationBeanDefinitionModifyManager
import com.hexagonal.systemconstructiondi.factory.*
import com.hexagonal.systemconstructiondi.resolver.DICapableCustomAnnotationResolver
import com.hexagonal.systemconstructiondi.resolver.DefaultBeanDefinitionResolver
import com.hexagonal.systemconstructiondi.resolver.DICapableAnnotationResolver

/**
 * system-construction-di 모듈의 객체 생성 팩토리
 * BeanFactoryPostProcessor는 빈 생성 이전에 실행되기에 스프링의 DI를 사용할 수 없음
 * 따라서 직접 의존관계 설정하여 객체 주입시켜줄 팩토리 필요함
 */
class AnnotationCapableInstanceFactory {
    companion object {
        fun getAnnotationCapableBeanFactory() : AnnotationCapableBeanFactory {
            val beanDefinitionModifyProcessor = DefaultBeanDefinitionResolver()
            val beanDefinitionModifyByAnnotManager = AnnotationBeanDefinitionModifyManager(beanDefinitionModifyProcessor)
            return CustomAnnotationCapableBeanFactory(beanDefinitionModifyByAnnotManager)
        }

        fun getDICapableAnnotationResolver() : DICapableAnnotationResolver {
            return DICapableCustomAnnotationResolver()
        }
    }

}
