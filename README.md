# NUMBERBOX-was

> 위 프로젝트는 N명의수학 백엔드 프로젝트입니다.  
> N명의수학은 초중고 수학교육과정에 맞춤화된 수학컨텐츠 제작 및 공유 플랫폼입니다.

## 개발기간

> **22.02 ~ 22.11(개발) : 웹서비스 구축 및 수학컨텐츠 제작**<br/>
> **22.11 ~ 23.07(운영) : 유지보수 및 기능 업데이트**<br/>
> **24.11 ~ 25.03(리팩토링) : 헥사고날 아키텍쳐 도입, 자바 to kotlin 전환, 테스트 커버리지 100% 달성**
<br/>

## Tech Stack

***

- Language: **Kotlin, Java 17**
- Architecture : **Hexagonal**
- Module-Structure : **Multi-Module**
- Framework: **Spring Boot 3.2.x**
- Persistence : **JPA, QueryDsl**
- Database: **MySQL 8.x**
- Cloud: **AWS (EC2, S3, RDS)**

  <br/>

## 시스템 구조

***

- ### 모듈 구조

```bash
.project
  ├─ app-domain
  ├─ app-service
  ├─ bootstrap
  ├─ infrastructure
  │     ├─ email-adapter
  │     ├─ hwp-client-adapter
  │     ├─ orm-jpa-adapter
  │     └─ storage-adapter
  └─ user-interface
        └─ rest-api

.modules
   ├─ auth-control
   ├─ auth-engine
   ├─ logging-control
   ├─ logging-engine
   ├─ mail-sender-control
   ├─ mail-sender-engine
   └─ system-construction
```

### project 모듈 소개

project는 멀티모듈 헥사고날 아키텍쳐 구조를 갖춘 NUMBERBOX-WAS 프로젝트 소스로 이루어져있다.  
시스템의 모든 흐름을 application layer(비즈니스)에서 결정하기에 모든 인터페이스가 application layer에 정의되어 있다.

- **app-service** : 비즈니스 로직 수행
- **app-domain** : 비즈니스 모델(dto, vo)로 구성
- **bootstrap** : 구동 모듈
- intrastructure
    - **email-adapter** : email 전송 관련 로직 수행
    - **hwp-client-adapter** : 한글 파일 변환 서버와 연동되며 한글 파일과 웹(html) 변환 처리 로직 수행
    - **orm-jpa-adapter** : 영속화 레이어
    - **storage-adapter** : s3와 연동하여 파일 저장 및 삭제
- user-interface
    - **rest-api** : controller로 이루어짐

### modules 소개

project에서 사용하는 모듈로 3rd-party-library에 대한 의존성을 약화시키기 위한 목적이다.  
pojo 방식으로 구현된 control 모듈과 라이브러리를 의존하고 구체적인 기능 구현을 하는 engine 모듈로 이루어졌다.  
`project 모듈 → (pojo)control 모듈 ← engine 모듈`  와 같은 의존성 방향으로 project와 라이브러리 결합도를 낮출 수 있다.

- **auth** : 인증 및 인가
- **logging** : api 요청 및 응답 로깅
- **mail-sender** : 메일 서버와 연동
- **system-construction** : DI와 트랜잭션 기능을 제공하는 모듈로 pojo로 구성된 project의 app 모듈에 기능 지원

**[참고]** project 및 modules가 갖춘 모듈의 상세 소개는 해당 모듈 read-me에 명시
<br/><br/>

## Code Convention

***

### interface & class 작명 규칙

- 읽기 작업은 Read, 쓰기작업은 Write 용어로 구분한다.
- 비즈니스 레이어의 인터페이스는 ReadCase, WriteCase라는 postFix를 갖는다.
- 비즈니스 레이어 이후 호출되는 레이어의 인터페이스는 Port라는 postFix를 갖는다.
    - port 인터페이스는 비즈니스 레이어(app-service)에서 정의한다.

| 모듈                                  | 구분        | 클래스명                  | 역할                      |
|-------------------------------------|-----------|-----------------------|-------------------------|
| app-service                         | interface | Xxx__ReadCase         | 비즈니스 로직의 읽기 작업 명세서      |
|                                     |           | Xxx__WriteCase        | 비즈니스 로직의 쓰기 작업 명세서      |
|                                     | class     | Xxx__ReadService      | Xxx__ReadCase의 구현체      |
|                                     |           | Xxx__WriteService     | Xxx__WriteCase의 구현체     |
|                                     | interface | Xxx__OrmPort          | 영속화 레이어 작업 명세서          |
|                                     |           | Xxx__EmailPort        | Email 전송 작업 명세서         |
|                                     |           | Xxx__HwpClientPort    | Hwp파일 변환 변환 명세서         |
|                                     |           | Xxx__StoragePort      | 파일 저장 및 삭제 관리 명세서       |
| infrastructure : email-adapter      | class     | Xxx__EmailAdapter     | Xxx__EmailPort의 구현체     |
| infrastructure : hwp-client-adapter |           | Xxx__HwpClientAdapter | Xxx__HwpClientPort의 구현체 |
| infrastructure : orm-jpa-adapter    |           | Xxx__Repository       | Xxx__ReadOrmPort의 구현체   |
| infrastructure : storage-adapter    |           | Xxx__S3StorageAdapter | Xxx__StoragePort의 구현체   |
| user-interface : rest-api           |           | Xxx__ReadController   | 읽기 작업 전용 컨트롤러           |
|                                     |           | Xxx__WriteController  | 쓰기 작업 전용 컨트롤러           |

```
예시.

|--presentation--|----------------business-------------------|----adapter----|

 ReadController -> ReadCase: ReadService -------> ReadOrmPort: ReadRepository


                                             ┌--> WriteOrmPort: WriteRepostiory
                                             |
                                             |--> EmailPort: EmailAdapter
 WriteController -> WriteCase: WriteService -|
                                             |--> HwpClientPort: HwpClientAdapter
                                             |
                                             └--> StoragePort: S3StorageAdapter
```

### dto 작명 규칙

| 모듈                               | 클래스명          | 역할                                              |
|----------------------------------|---------------|-------------------------------------------------|
| app-domain                       | Xxx__Dto      | application-layer 메서드의 input 객체                 |
|                                  | Xxx__Vo       | application-layer 메서드의 output 객체, 비즈니스 로직 수행 결과 |
| infrastructure : orm-jpa-adapter | Xxx__Entity   | 영속화 객체                                          |
| user-interface : rest-api        | Xxx__Request  | 클라이언트 요청 데이터                                    |
| user-interface : rest-api        | Xxx__Response | 서버 응답 데이터                                       |

<br/><br/>

## 테스트 환경 소개

***

모듈 단위로 단위테스트가 작성되어 있으며 각 모듈 내에 테스트 환경 설정과 테스트 더블 등이 존재한다.

### 패키지 구조

```
.src
  ├─ main
  ├─ test
  └─ testFixtures
        ├─ constant
        ├─ mock
        └─ sample
```

- 모든 테스트 test 하위에 작성되어있다.
- testFixtures 하위에 테스트 환경 설정과 테스트 더블, 테스트용 샘플 데이터가 존재한다.
    - constant : 상수파일로 테스트 더블의 메서드 인자값으로 사용되며 이 값에 따라 테스트 더블의 동작을 제어한다.
    - mock : 테스트 더블이 존재하는 패키지이다.
    - sample : 테스트시 사용되는 데이터로 메서드의 인지값과 반환값을 팩토리 메서드 형태로 제공한다.

### 모듈별 테스트 환경

#### [app-service]

- app-service 모듈에서 프로젝트의 모든 인터페이스를 정의하므로 테스트 더블 또한 app-service에 모두 존재한다.
    - user-interface나 adapter가 변경되더라도 영향 없이 재사용하기 위해서
- 테스트 및 테스트 환경 구축을 위해 사용된 모든 도구는 순수 코틀린 코드로 작성되어있다.
    - 테스트 검증 목적의 junit 외의 어떠한 테스트 프레임워크도 사용하지 않는다.

#### [user-interface : rest-api]

- controller 테스트를 위한 mockMvc 테스트 환경이 구축되어있다.
    - controller가 호출하는 서비스 레이어 등 의존객체는 모두 모킹처리 한다. (오직 컨트롤러만 테스트 목적)  
      ``com.kamcci.numberbox.restapi.config.RestApiWebMvcMockBeanConfig`` 어노테이션에 config 정의
    - mockMvc 테스트에서 http 메서드에 따른 요청 형식을 유틸화한 클래스를 상속 받아 빠르게 테스트 할 수 있다.  
      ```com.kamcci.numberbox.restapi.common.BaseMockMvcTest```
- controller를 제외한 다른 대상은 순수 코드로 테스트한다.

#### [infrastructure : orm-jpa-adapter]

- repository 테스트를 위한 DataJpaTest 환경이 구축되어있다.
    - 테스트 DB는 테스트 컨테이너를 통해 생성됨
    - flyway를 통해 테스트 DB에 테이블 스키마와 테스트 데이터를 생성함
    - DataJpaTest의 자동 롤백을 통해 테스트 DB의 상태를 일관되게 유지함
    - ```com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest``` 어노테이션에 Repository 테스트 환경 구성
- repository를 제외한 나머지 대상은 프레임워크 도움 없이 순수 프로그래밍 코드로 작성

#### 나머지 모듈

- 테스트 코드는 test, 테스트 도구 및 환경 구축은 testFixtures에 이루어져 있음
- 순수 코틀린 코드로 이루어져있는 app-service의 테스트 환경과 유사
  <br/><br/>

### Test Code Convention

- 테스트 더블은 모킹 대상 인터페이스명에 앞에 Mock을 붙인다.

```kotlin
class MockMemberWriteOrmPort : MemberWriteOrmPort
```

- 테스트시 인자값 또는 반환값으로 사용되는 객체는 아래와 같이 SampleData라는 클래스에서 `get실제객채명` 메서드명 규칙을 따른다.

```kotlin
object MemberSampleData {
    fun getMemberSignupDto(email: String = "signup@test.com") = MemberSignUpDto(
        email = email,
        password = "1234"
    )
```

- orm-jpa-adapter에서 flyway로 insert한 데이터의 모든 칼럼값을 프로그래밍 코드로 나타내기 어려우므로  
  아래와 같이 조인 칼럼, 검색 조건으로 자주 사용되는 값들을 표현하여 테스트 검증시 사용할수 있는 값을 제공한다.
    - 클래스명은 실제 엔티명 뒤에 DummyFactory라고 붙인다.

```kotlin
object MemberDummyFactory {
    // 계정 비활성 목적
    fun getMemberDummyEntity4Disable() = UUID.fromString("82CA3122-CDA8-EA4D-9BC7-037CB86FDB20")

    // 임시 비밀번호 발급 계정
    fun getTmpPwMemberId() = UUID.fromString("32ca3122-cda8-ea4d-9bc7-037cb86fdb20")

    class ExistEntityInfo(
        val memberId: UUID,
        val email: String,
        val userName: String? = null,
        val phone: String? = null
    )
}
```