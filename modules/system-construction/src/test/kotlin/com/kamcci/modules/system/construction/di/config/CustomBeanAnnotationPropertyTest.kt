//package com.kamcci.modules.system.construction.di.config
//
//import com.kamcci.modules.system.construction.sample.CustomBean
//import com.kamcci.modules.system.construction.sample.CustomPrimary
//import com.kamcci.modules.system.construction.sample.CustomQualifier
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertDoesNotThrow
//import org.junit.jupiter.api.assertThrows
//
//class CustomBeanAnnotationPropertyTest {
//    @Test
//    fun `annotation 타입 조회 - 성공 `() {
//        // given
//        val annotationProps = getCustomAnnotationProperty()
//
//        // when & then
//        assertDoesNotThrow {
//            annotationProps.customBean
//            annotationProps.basePackage
//            annotationProps.beanScope
//            annotationProps.primary
//            annotationProps.qualifier
//        }
//        assertThat(annotationProps.getCustomBeanAnnotation() == CustomBean::class).isTrue()
//        assertThat(annotationProps.getPrimaryAnnotation() == CustomPrimary::class).isTrue()
//        assertThat(annotationProps.getQualifierAnnotation() == CustomQualifier::class).isTrue()
//    }
//
//    @Test
//    fun `annotation 타입 조회 - 실패(annotation class 아님) `() {
//        // given
//        val annotationProps = getNonAnnotationProperty()
//
//        // when & then
//        assertThrows<ClassNotFoundException> {
//            annotationProps.getCustomBeanAnnotation()
//        }
//    }
//}
