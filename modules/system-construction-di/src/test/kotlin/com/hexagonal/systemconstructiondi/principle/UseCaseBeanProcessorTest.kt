package com.hexagonal.systemconstructiondi.principle

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.support.AutowireCandidateQualifier
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

// 테스트 목적 tmp 클래스(빈으로 등록될 대상 클래스)
class TmpClass

@SpringBootTest
class UseCaseBeanProcessorTest(
    @Autowired private val context: ApplicationContext,
) {

    @Test
    fun `스프링부트 autoconfig 컨텍스트 구현체 확인`() {
        assertInstanceOf(AnnotationConfigApplicationContext::class.java, context)
    }

    @Test
    fun `빈 정의를 통한 빈 생성`() {
        // given
        // 빈 등록 대상 클래스 및 속성
        val beanClass = TmpClass::class.java
        val beanName = TmpClass::class.java.simpleName
        val beanScope = "singleton"
        val isPrimary = true
        val qualifierValues = "tmp, default"

        // BeanDefinition 생성
        val beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass)

        // 싱글톤 설정
        beanDefinitionBuilder.beanDefinition.scope = beanScope

        // primary 설정
        beanDefinitionBuilder.beanDefinition.isPrimary = isPrimary

        // qualifier 설정(빈 등록 대상 객체의 속성만 정의,
        // autowire과 함께 필드에 적용되어 di 되는 기능은 이외 별도 처리 필요)
        qualifierValues.forEach { alias ->
            val qualifier = AutowireCandidateQualifier(Qualifier::class.java)
            qualifier.setAttribute("value", alias)
            beanDefinitionBuilder.beanDefinition.addQualifier(qualifier)
        }

        // 컨텍스트에 beanDefinition 등록
        val autoConfigContext = context as AnnotationConfigApplicationContext
        autoConfigContext.registerBeanDefinition(beanName, beanDefinitionBuilder.beanDefinition)

        // when
        // context에 beanDefinition 있고 인스턴스 없다면 생성하여 제공
        val bean = autoConfigContext.getBean(beanName)
        val beanDef = autoConfigContext.getBeanDefinition(beanName)

        // then
        assertInstanceOf(TmpClass::class.java, bean)
        assertThat(beanDef.isPrimary).isTrue()
    }
}
