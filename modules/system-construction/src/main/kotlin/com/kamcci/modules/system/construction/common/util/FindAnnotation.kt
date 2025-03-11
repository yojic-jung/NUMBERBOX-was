package com.kamcci.modules.system.construction.common.util

import java.lang.reflect.Method

object FindAnnotation {
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