package com.kamcci.modules.system.construction.tx.pointcut

import com.kamcci.modules.system.construction.common.util.FindAnnotation.findInterfaceAnnotation
import org.springframework.aop.MethodMatcher
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut
import java.lang.reflect.Method

class CustomAnnotationMatchingPointcut(
    private val annotation: Class<out Annotation>
) : AnnotationMatchingPointcut(null, annotation) {

    override fun getMethodMatcher(): MethodMatcher = CustomMethodMatcher(annotation)

    // 클래스와 인터페이스 모두에서 어노테이션을 찾는 메서드 매처
    private class CustomMethodMatcher(private val annotation: Class<out Annotation>) : MethodMatcher {
        override fun matches(method: Method, targetClass: Class<*>): Boolean {
            val isInterfaceAnnotation = findInterfaceAnnotation(annotation, method) != null
            return method.isAnnotationPresent(annotation) ||
                    targetClass.isAnnotationPresent(annotation) ||
                    isInterfaceAnnotation
        }

        /**
         * true : 컴파일시에만 matches 실행
         * false: 런타임시에도 matches 실행
         */
        override fun isRuntime() = false

        // 메서드, 클래스 둘 다 검사하는 매칭
        override fun matches(method: Method, targetClass: Class<*>, vararg args: Any?): Boolean {
            return matches(method, targetClass)
        }
    }
}
