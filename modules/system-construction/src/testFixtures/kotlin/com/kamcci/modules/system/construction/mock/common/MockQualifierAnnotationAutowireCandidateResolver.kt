package com.kamcci.modules.system.construction.mock.common

import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver

// protected 메서드인 isQualifier에 접근하기 위하여 하위 타입 구현
class MockQualifierAnnotationAutowireCandidateResolver : QualifierAnnotationAutowireCandidateResolver() {
    fun hasQualifier(annotationType: Class<out Annotation>): Boolean {
        return isQualifier(annotationType)
    }
}