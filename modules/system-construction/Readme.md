# system-constrcution 모듈

***

### 개요

- system-constrcution 모듈은 프로그램 시스템을 구축하는데 필요한 기능을 제공해주는 모듈이다.

### 제공 기능

1. DI(Dependency Injection)
2. Transaction

## 사용법

### DI (Dependency Injection)

1. 아래 주석에서 설명한 기능을 제공 받을 pojo 방식의 커스텀 어노테이션을 상수파일에 지정

```kotlin
object CustomDIAnnotationBeanConstConfig {
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
```

해당 어노테이션을 부착하면 system-construction 모듈에서 기능을 제공함

### Transaction

1. 트랜잭션 기능을 제공받을 pojo 방식의 커스텀 어노테이션을 상수파일에 지정

```kotlin
object CustomTxUserConfig {
    val CUSTOM_TX_ANNOTATION = TXExecute::class
}
```

2. 사용하는 모듈에서 all-open 커스텀 어노테이션 경로 지정 필요
    - 스프링 aop를 통해 트랜잭션을 지원하므로 트랜잭션 대상 클래스는 final 선언 되어있으면 안됨

## 구현 상세

### DI

- DI 기능을 제공하는 원리는 스프링의 빈으로 등록하여 DI 기능이 지원되도록 한다.
- 스프링 빈 등록 절차는 스프링의 기본 절차인 BeanDefinition을 BeanDefinitionRegistry에 저장시키면 인스턴스화 이후 등록되는 절차를 따른다.
    - BeanFactoryPostProcessor : BeanDefinition 생성 및 변경 책임을 갖으며 아래 객체에게 처리를 위임
    - AnnotationCapableBeanRegistrar : 후보군 스캔 및 BeanDefinition 생성
    - BeanDefinitionPropertyProcessor : Primary, 별칭, 스코프 추출하고 BeanDefinition에 속성 할당

### Transaction

- 스프링 aop를 통해 트랜잭션 기능을 제공함
- pointcut은 사용자가 지정한 어노테이션을 부착한 클래스, 인터페이스의 메서드
- advice로 등록된 MethodInterceptor는 메서드 실행정보를 파라미터로 전달받고 이를 통해 클래스, 인터페이스에 적용된 커스텀 어노테이션의 정보를 추출하여 트랜잭션 기능 제공

### [참고]

25.3.11 [system-construction 모듈 리플렉션 방식 제거] 커밋 이후 버전은 project에서 정의한 어노테이션을 의존하는 구조를 갖추고 있음.  
위 커밋 이전 버전은 리플렉션을 통해 DI와 트랜잭션을 제공받으려는 어노테이션을 문자열로 전달받음.  
의존관계를 완전히 끊어 내려면 리플렉션을 사용하면 됨.  
다만 리플렉션은 문자열로 클래스 경로를 전달받아 기능 지원을 하므로 기능 구현시 컴파일러에 의해 타입 체크를 할 수 없는 단점으로 변경사항에 민감하게 반응함  
따라서 현재는 리플렉션이 아닌 project : app-domain을 의존하여 컴파일러에 의해 타입체크를 할 수 있는 방식으로 제공됨