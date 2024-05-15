package com.numberbox.systemconstructiondi.util

import com.numberbox.systemconstructiondi.factory.AnnotationCapableBeanFactory
import com.numberbox.systemconstructiondi.factory.CustomAnnotationCapableBeanFactory
import com.numberbox.systemconstructiondi.manager.AnnotationBeanDefinitionModifyManager
import com.numberbox.systemconstructiondi.resolver.DICapableAnnotationResolver
import com.numberbox.systemconstructiondi.resolver.DICapableCustomAnnotationResolver
import com.numberbox.systemconstructiondi.resolver.DefaultBeanDefinitionResolver

/**
 * system-construction-di 모듈의 객체 생성 팩토리
 * BeanFactoryPostProcessor는 빈 생성 이전에 실행되기에 스프링의 DI를 사용할 수 없음
 * 따라서 직접 의존관계 설정하여 객체 주입시켜줄 팩토리 필요함
 */
class AnnotationCapableInstanceFactory {
    companion object {
        fun getAnnotationCapableBeanFactory(): AnnotationCapableBeanFactory {
            val beanDefinitionModifyProcessor = DefaultBeanDefinitionResolver()
            val beanDefinitionModifyByAnnotManager =
                AnnotationBeanDefinitionModifyManager(beanDefinitionModifyProcessor)
            return CustomAnnotationCapableBeanFactory(beanDefinitionModifyByAnnotManager)
        }

        fun getDICapableAnnotationResolver(): DICapableAnnotationResolver {
            return DICapableCustomAnnotationResolver()
        }
    }
}
