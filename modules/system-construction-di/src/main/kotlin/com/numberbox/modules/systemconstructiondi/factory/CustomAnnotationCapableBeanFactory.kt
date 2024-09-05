package com.hexagonal.systemconstructiondi.factory

import com.hexagonal.systemconstructiondi.manager.BeanDefinitionControlManager
import org.springframework.beans.BeansException
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import kotlin.reflect.KClass

class CustomAnnotationCapableBeanFactory(
    private val beanDefinitionControlManager: BeanDefinitionControlManager,
) : AnnotationCapableBeanFactory {
    @Throws(BeansException::class)
    override fun registerOnlyWith(
        customBeanAnnotation: KClass<out Annotation>,
        basePackages: String,
        registry: BeanDefinitionRegistry,
    ) {
        // annotation filter 등록
        val componentScanner = ClassPathScanningCandidateComponentProvider(false)
        val annotationFilter = AnnotationTypeFilter(customBeanAnnotation.java)
        componentScanner.addIncludeFilter(annotationFilter)

        // basePackage에 대하여 빈 후보군 beanDefinition 추출
        val basePackageArr = basePackages.split(",").map { it.trim() }
        val beanDefOfCandidates = basePackageArr.flatMap { basePackage ->
            componentScanner.findCandidateComponents(basePackage)
        }.toSet()

        // 빈 후보들에 대하여 빈 정의 설정 및 등록
        for (beanDef in beanDefOfCandidates) {
            val beanClass = Class.forName(beanDef.beanClassName)
            val beanName = beanClass.simpleName.replaceFirstChar { it.lowercase() }
            val beanDefBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass)

            // manager에게 beanDefinition 조작 위임
            beanDefinitionControlManager.delegate(beanClass, beanDefBuilder)

            // 빈 등록
            registry.registerBeanDefinition(beanName, beanDefBuilder.beanDefinition)
        }
    }
}
