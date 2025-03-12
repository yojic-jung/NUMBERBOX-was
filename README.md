# NUMBERBOX-was

> 위 프로젝트는 N명의수학 백엔드 프로젝트입니다.  
> N명의수학은 초중고 수학교육과정에 맞춤화된 수학컨텐츠 제작 및 공유 플랫폼입니다.

## 개발기간

> **22.02 ~ 22.11(개발) : 웹서비스 구축 및 수학컨텐츠 제작**<br/>
> **22.11 ~ 23.07(운영) : 유지보수 및 기능 업데이트**<br/>
> **24.11 ~ 25.03(리팩토링) : 헥사고날 아키텍쳐 도입, 자바 to kotlin 전환, 관리자 제외**
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
