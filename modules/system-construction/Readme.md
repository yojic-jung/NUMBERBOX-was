# system-constrcution 모듈

### 개요

- system-constrcution 모듈은 프로그램 시스템을 구축하는데 필요한 기능을 제공해주는 모듈이다.

### 제공 기능

1. DI(Dependency Injection)
2. Transaction

## 사용법

### 1. DI (Dependency Injection)

### 2. Transaction

- 사용하는 모듈에서 all-open 커스텀 어노테이션 경로 지정 필요
    - 스프링 aop를 통해 트랜잭션을 지원하므로 타깃 객체를 상속한 프록시가 만들어져야하므로 클래스에 final 선언되어있으면 안됨

### 버전 관리

v1. DI  : 의존주입, 같은 상위 타입의 구현체 중 우선순위 주입 설정, 의존주입 별칭 사용  
tx : 전파수준, 고립성, readOnly 수준  
메서드, 클래스에 대해 적용

향후 2.0 계획
커스텀 어노테이션 컴파일 의존 아닌 런타임 의존으로 변경
config 파일에 직접 import 아닌 런타임에 클래스 파일 경로 입력받아 설정될 수 있도록 지원  
(모듈 의존성 제거 목적)

