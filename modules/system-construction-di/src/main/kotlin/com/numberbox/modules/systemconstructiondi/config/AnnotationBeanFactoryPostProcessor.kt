package com.hexagonal.systemconstructiondi.config

import com.hexagonal.systemconstructiondi.config.CustomAnnotationBeanConfigHolder.Companion.BASE_PACKAGES
import com.hexagonal.systemconstructiondi.config.CustomAnnotationBeanConfigHolder.Companion.CUSTOM_BEAN_ANNOTATION
import com.hexagonal.systemconstructiondi.config.CustomAnnotationBeanConfigHolder.Companion.CUSTOM_QUALIFIER_ANNOTATION
import com.hexagonal.systemconstructiondi.factory.AnnotationCapableBeanFactory
import com.hexagonal.systemconstructiondi.resolver.DICapableAnnotationResolver
import com.hexagonal.systemconstructiondi.util.AnnotationCapableInstanceFactory
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.stereotype.Component

/**
 * Def : 사용자가 정의한 어노테이션이 붙은 클래스의 beanDefinition 등록 Processor
 * Desc: BeanFactoryPostProcessor은 BeanDefinition을 조작할 수 있음
 *       BeanDefinition이 등록되어 있으면 BeanPostProcessor가 해당 정의를 기반으로 인스턴스화함
 */
@Component
class AnnotationBeanFactoryPostProcessor(
    private val annotationCapableBeanFactory: AnnotationCapableBeanFactory = AnnotationCapableInstanceFactory.getAnnotationCapableBeanFactory(),
    private val diCapableAnnotationResolver: DICapableAnnotationResolver = AnnotationCapableInstanceFactory.getDICapableAnnotationResolver(),
) : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        // 커스텀 어노테이션 붙은 클래스 beanDefinition으로 생성
        val registry = beanFactory as BeanDefinitionRegistry
        annotationCapableBeanFactory.registerOnlyWith(CUSTOM_BEAN_ANNOTATION, BASE_PACKAGES, registry)

        // 의존(참조속성)관계 설정에 @Qualifier처럼 사용될 어노테이션 타입 지정
        diCapableAnnotationResolver.add(CUSTOM_QUALIFIER_ANNOTATION, beanFactory as DefaultListableBeanFactory)
    }
}
