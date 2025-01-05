package com.kamcci.modules.system.construction.di.registrar

import com.kamcci.modules.system.construction.di.config.CustomBeanAnnotationProperty
import com.kamcci.modules.system.construction.di.processor.BeanDefinitionPropertyProcessor
import org.springframework.beans.BeansException
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter

class CustomAnnotationCapableBeanRegistrar(
    private val beanDefinitionPropertyProcessor: BeanDefinitionPropertyProcessor,
) : AnnotationCapableBeanRegistrar {

    @Throws(BeansException::class)
    override fun registerOnlyWith(
        customAnnotationProperty: CustomBeanAnnotationProperty,
        registry: BeanDefinitionRegistry,
    ) {
        // annotation filter 등록
        val componentScanner = ClassPathScanningCandidateComponentProvider(false)
        val annotationFilter = AnnotationTypeFilter(customAnnotationProperty.getCustomBeanAnnotation().java)
        componentScanner.addIncludeFilter(annotationFilter)

        // basePackage에 대하여 빈 후보군 beanDefinition 추출
        val basePackageArr = customAnnotationProperty.basePackage.split(",").map { it.trim() }
        val beanDefOfCandidates = basePackageArr.flatMap { basePackage ->
            componentScanner.findCandidateComponents(basePackage)
        }.toSet()

        // 빈 후보들에 대하여 빈 정의 설정 및 등록
        for (beanDef in beanDefOfCandidates) {
            val beanClass = Class.forName(beanDef.beanClassName)
            val beanName = beanClass.simpleName.let { it[0].lowercase() + it.substring(1) }
            val beanDefBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass)

            // manager에게 beanDefinition 조작 위임
            beanDefinitionPropertyProcessor.modify(customAnnotationProperty, beanClass, beanDefBuilder)

            // 빈 등록
            registry.registerBeanDefinition(beanName, beanDefBuilder.beanDefinition)
        }
    }
}
