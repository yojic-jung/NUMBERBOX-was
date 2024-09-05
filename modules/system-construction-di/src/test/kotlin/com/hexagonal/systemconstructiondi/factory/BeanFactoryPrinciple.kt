package com.hexagonal.systemconstructiondi.factory

import com.hexagonal.appservice.test.MyCustomInterface
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Controller

@Controller
class TmpController

@SpringBootTest
class CustomAnnotationCapableBeanFactoryTest(
    @Autowired
    @Qualifier("secondary")
    private val myCustomInterface: MyCustomInterface,
) {
    @Autowired
    lateinit var applicationContext: AnnotationConfigApplicationContext

    @Test
    fun printAllBeanNames() {
        myCustomInterface.a()
        val beanNames = applicationContext.beanDefinitionNames
        println("All Bean Names:")
        beanNames.forEach { beanName ->
            val simpleBeanName = beanName.substringAfterLast(".") // 패키지명을 제외한 빈의 이름만 추출
            println(simpleBeanName)
        }
    }

    @Test
    fun checkQualifier() {
        myCustomInterface.a()
    }

    @Test
    fun `autoConfig beanDefition 확인`() {
        val beanDef = applicationContext.beanFactory.getBeanDefinition("tmpController")
        val beanDef2 = applicationContext.beanFactory.getBeanDefinition("memberSaveDefaultService")
        println(beanDef)
        println(beanDef2)
    }
}
