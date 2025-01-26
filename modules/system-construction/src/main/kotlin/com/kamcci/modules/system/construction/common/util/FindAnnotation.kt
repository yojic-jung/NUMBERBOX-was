@file:Suppress("UNCHECKED_CAST")

package com.kamcci.modules.system.construction.common.util

import java.lang.reflect.Method
import kotlin.reflect.KClass

object FindAnnotation {
    // 클래스 경로를 통해 어노테이션 타입 반환
    fun getAnnotationClass(annotationClassName: String): KClass<out Annotation> {
        // 클래스 경로로부터 Class 객체를 얻음
        val clazz = Class.forName(annotationClassName).kotlin
        // Annotation 서브클래스인지 확인하고 반환
        if (Annotation::class.java.isAssignableFrom(clazz.java)) {
            return clazz as KClass<out Annotation>  // 안전하게 Annotation 타입으로 캐스팅
        } else {
            throw ClassNotFoundException()
        }
    }

    // 인터페이스에서 어노테이션 찾기
    fun findInterfaceAnnotation(targetAnnotation: Class<out Annotation>, method: Method): Any? {
        for (iface in method.declaringClass.interfaces) {
            try {
                // 메서드에서 찾기
                val methodAnnotation = iface.getDeclaredMethod(method.name, *method.parameterTypes)
                if (methodAnnotation.isAnnotationPresent(targetAnnotation)) {
                    return methodAnnotation.getAnnotation(targetAnnotation)
                }

                // 클래스에서 찾기
                val classAnnotation = iface.getAnnotation(targetAnnotation)
                if (classAnnotation != null) {
                    return classAnnotation
                }
            } catch (e: NoSuchMethodException) {
                // nothing 아무것도 하지 않음
                // try-catch 없을시 빈 생성 과정에서 오류남
            }
        }
        return null
    }
}