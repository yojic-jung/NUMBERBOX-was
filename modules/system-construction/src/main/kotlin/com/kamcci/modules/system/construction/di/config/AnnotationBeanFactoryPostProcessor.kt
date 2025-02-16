package com.kamcci.modules.system.construction.di.config

import com.kamcci.modules.system.construction.di.registrar.AnnotationCapableBeanRegistrar
import com.kamcci.modules.system.construction.di.registrar.QualifierAnnotationRegistrar
import com.kamcci.modules.system.construction.di.util.AnnotationCapableInstanceFactory.getAnnotationCapableBeanFactory
import com.kamcci.modules.system.construction.di.util.AnnotationCapableInstanceFactory.getDICapableAnnotationResolver
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

/**
 * Def : 사용자가 정의한 어노테이션이 붙은 클래스의 beanDefinition 등록 Processor
 * Desc: BeanFactoryPostProcessor은 BeanDefinition을 조작할 수 있음
 *       BeanDefinition이 등록되어 있으면 BeanPostProcessor가 해당 정의를 기반으로 인스턴스화함
 */
@Component
class AnnotationBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    // BeanFactoryPostProcessor는 빈 생성 이전에 실행 되므로 의존성을 스스로 갖춰야함
    private val annotationCapableBeanRegistrar: AnnotationCapableBeanRegistrar = getAnnotationCapableBeanFactory()
    private val qualifierAnnotationRegistrar: QualifierAnnotationRegistrar = getDICapableAnnotationResolver()

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val customAnnotationProperty = getCustomAnnotaionProperty(beanFactory)
        // 커스텀 어노테이션 붙은 클래스 beanDefinition으로 생성
        val registry = beanFactory as BeanDefinitionRegistry
        annotationCapableBeanRegistrar.registerOnlyWith(customAnnotationProperty, registry)

        // 의존(참조속성)관계 설정에 @Qualifier처럼 사용될 어노테이션 타입 지정
        qualifierAnnotationRegistrar.add(
            customAnnotationProperty.getQualifierAnnotation(),
            beanFactory as DefaultListableBeanFactory
        )
    }

    // BeanFactoryPostProcessor의 초기화 시점이 @Configuration보다 이르기에 environment로 yml 변수 직접 접근해야함
    private fun getCustomAnnotaionProperty(beanFactory: ConfigurableListableBeanFactory): CustomBeanAnnotationProperty {
        val environment = beanFactory.getBean(Environment::class.java)
        return CustomBeanAnnotationProperty(
            environment.getProperty("system.construction.di.class-path.customBean").toString(),
            environment.getProperty("system.construction.di.class-path.basePackage").toString(),
            environment.getProperty("system.construction.di.class-path.beanScope").toString(),
            environment.getProperty("system.construction.di.class-path.primary").toString(),
            environment.getProperty("system.construction.di.class-path.qualifier").toString(),
        )
    }
}
