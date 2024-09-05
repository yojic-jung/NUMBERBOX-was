package com.hexagonal.systemconstructiondi.config

import com.hexagonal.appdomain.annotation.Aliases
import com.hexagonal.appdomain.annotation.Priority
import com.hexagonal.appdomain.annotation.UseCase

/**
 * Def : 의존관계 설정 제공받을 사용자 정의 어노테이션 설정 홀더
 */
class CustomAnnotationBeanConfigHolder {
    companion object {
        // 사용자 정의 빈 등록 대상 어노테이션
        val CUSTOM_BEAN_ANNOTATION = UseCase::class

        // 스캔 대상이 될 기본 패키지 경로들. 컴마(,) 로 구분하여 복수개 패키지 경로 설정 가능
        const val BASE_PACKAGES = "com.hexagonal.appservice"
        
        // 빈 스코프 정의
        const val BEAN_SCOPE = "singleton"

        // 상위 타입의 하위 구현체 중 최우선 순위를 주기 위해 사용할 어노테이션(like @Primary)
        val CUSTOM_PRIMARY_ANNOTATION = Priority::class

        // 상위 타입의 하위 구현체들에게 각각 별칭을 주기 위해 사용할 어노테이션(like @Qualifier)
        val CUSTOM_QUALIFIER_ANNOTATION = Aliases::class
    }
}
