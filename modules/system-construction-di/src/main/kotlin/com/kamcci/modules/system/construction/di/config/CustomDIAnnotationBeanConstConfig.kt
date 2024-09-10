package com.kamcci.modules.system.construction.di.config

import com.kamcci.numberbox.app.domain.system_construction.Aliases
import com.kamcci.numberbox.app.domain.system_construction.Priority
import com.kamcci.numberbox.app.domain.system_construction.UseCase

/**
 * Def : 의존관계 설정 제공받을 사용자 정의 어노테이션 설정
 */
object CustomDIAnnotationBeanConstConfig {
    // todo application.yml 설정으로 변경하여 의존성 제거
    // 사용자 정의 빈 등록 대상 어노테이션
    val CUSTOM_BEAN_ANNOTATION = UseCase::class

    // 스캔 대상이 될 기본 패키지 경로들. 컴마(,) 로 구분하여 복수개 패키지 경로 설정 가능
    const val BASE_PACKAGES = "com.kamcci.numberbox.app"

    // 빈 스코프 정의
    const val BEAN_SCOPE = "singleton"

    // 상위 타입의 하위 구현체 중 최우선 순위를 주기 위해 사용할 어노테이션(like @Primary)
    val CUSTOM_PRIMARY_ANNOTATION = Priority::class

    // 상위 타입의 하위 구현체들에게 각각 별칭을 주기 위해 사용할 어노테이션(like @Qualifier)
    val CUSTOM_QUALIFIER_ANNOTATION = Aliases::class
}
