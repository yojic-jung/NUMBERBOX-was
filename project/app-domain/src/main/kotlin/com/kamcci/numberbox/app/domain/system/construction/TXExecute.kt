package com.kamcci.numberbox.app.domain.system.construction

import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * Def. 데이터베이스 트랜잭션을 적용 시켜주는 커스텀 어노테이션
 * Desc.
 * - 클래스와 메서드에 적용 가능(인터페이스 미지원)
 *
 * @param readOnly 읽기전용 여부
 * @param propagation 트랜잭션 전파
 * @param isolation 트랜잭션 고립성 수준
 */
@Target(ElementType.TYPE, ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class TXExecute(
    val readOnly: Boolean = false,
    val propagation: Propagation = Propagation.REQUIRED,
    val isolation: Isolation = Isolation.DEFAULT,
)


/**
 * 트랜잭션 전파 수준
 */
enum class Propagation(val id: Int) {
    /**
     * 부모 트랜잭션 존재한다면 합류, 그렇지 않으면 새로운 트랜잭션 생성
     * 부모나 자식 예외시 둘다 롤백
     */
    REQUIRED(0),

    // 트랜잭션을 필요로 하지 않지만 진행 중인 트랜잭션이 존재하면 트랜잭션 사용(트랜잭션 미 존재시에도 메소드 정상 동작)
    SUPPORTS(1),

    // 부모 트랜잭션에 합류(부모 트랜잭션 미존재시 예외 발생)
    MANDATORY(2),

    // 무조건 새로운 트랜잭션 생성(nested한 방식이라도 롤백은 각각 이루어짐)
    REQUIRES_NEW(3),

    // 트랜잭션 중단(예외 발생은 안함)
    NOT_SUPPORTED(4),

    // 트랜잭션 사용 안함(진행 중 트랜잭션 존재시 예외 발생)
    NEVER(5),

    /**
     * REQUIRED 처럼 동작
     * But, 자식 예외 부모에 영향 안줌(부모 예외시에만 자식까지 롤백)
     */
    NESTED(6);
}

/**
 * 데이터베이스 트랜잭션 고립성 수준
 */
enum class Isolation(val id: Int) {
    // DB 디폴트 고립성 수준 그대로 사용
    DEFAULT(-1),

    // 아직 커밋 되지 않은 다른 트랜잭션의 변경사항 보임
    READ_UNCOMMITTED(1),

    // 커밋된 다른 트랜잭션의 변경사항만 보임, Oracle defualt
    READ_COMMITTED(2),

    // 다른 트랜잭션이 커밋을 해도 변경 데이터가 보이지 않음(나의 트랜잭션이 시작할 시점의 DB 스냅샷 시점), Mysql default
    REPEATABLE_READ(4),

    // 트랜잭션 작업 직렬화(동시 진행 트랜잭션 없음)
    SERIALIZABLE(8)
}
