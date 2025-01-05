package com.kamcci.modules.system.construction.di.util

import com.kamcci.modules.system.construction.di.registrar.AnnotationCapableBeanRegistrar
import com.kamcci.modules.system.construction.di.registrar.QualifierAnnotationRegistrar
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AnnotationCapableInstanceFactoryTest {
    @Test
    fun `AnnotationCapableBeanFactory 구현체 생성 및 조회 - 성공`() {
        // when
        val annotationCapableInstanceFactory = AnnotationCapableInstanceFactory.getAnnotationCapableBeanFactory()

        // then
        assertThat(annotationCapableInstanceFactory).isInstanceOf(AnnotationCapableBeanRegistrar::class.java)
    }

    @Test
    fun `DICapableAnnotationResolver 구현체 생성 및 조회 - 성공`() {
        // when
        val diCapableAnnotationResolver = AnnotationCapableInstanceFactory.getDICapableAnnotationResolver()

        // then
        assertThat(diCapableAnnotationResolver).isInstanceOf(QualifierAnnotationRegistrar::class.java)
    }
}