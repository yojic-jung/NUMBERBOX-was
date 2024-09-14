# Auth 모듈(인증, 인가 모듈)

## 개요

- auth 모듈은 로그인 인증 처리와 인증된 사용자에게 자원 접근을 허용하는 인가 처리를 진행한다.
- 인증, 인가 처리에는 두가지 방식이 존재한다.
    - 아이디-패스워드
    - jwt 토큰

## 사용법

1. 로그인 요청 url 지정

- 아래와 같이 auth 모듈을 의존하는 프로젝트에서 로그인 요청 url과 실패 콜백 url을 지정한다.
- 모든 요청의 httpMethod 타입은 디폴트로 POST가 제공된다.

```yml
auth:
  login:
    url:
      process: /login/general
      fail: /login/fail
``` 

2. 커스텀 속성 지정

- AuthConstantConfig.java 파일에서 아래와 같은 상수 값을 직접 지정할수 있다.
    - ex) 권한 prefix, 토큰 유효시간, 토큰명 등

```java
 // 시큐리티 권한 관리 role prefix
public static final String ROLE_PREFIX = "ROLE_";
// 액세스 토큰 유효시간
public static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간
/**
 * * 로그인 유지 요청 request 속성명
 * - 속성값이 LOGIN_KEEP_VAL인 경우 REFRESH_TOKEN_VALID_TIME_OP_KEEP으로 로그인 유지 시간 결정
 * - 속성값이 LOGIN_KEEP_VAL이 아닌 경우 REFRESH_TOKEN_VALID_TIME으로 로그인 유지 시간 결정
 */
public static final String LOGIN_KEEP_ATTR = "loginState";
// 로그인 유지 요청 속성값
public static final String LOGIN_KEEP_VAL = "keep";
public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 6; // 6시간
public static final long REFRESH_TOKEN_VALID_TIME_OP_KEEP = 1000L * 60 * 60 * 24 * 30; // 1달 (로그인 유지 요청한 경우)
// 클라이언트에 전달할 액세스 토큰 속성명
public static String ACCESS_TOKEN_NAME = "Authorization";
public static String TOKEN_STANDARD_PREFIX = "Bearer";
// 클라이언트에 전달할 리프레시 토큰 속성명
public static String REFRESH_TOKEN_NAME = "refresh-token";
// 클라이언트에 전달할 사용자 권한 속성명
public static String ROLE_NAME = "roles";
```

3. 로그인 성공 후처리

- 로그인 성공시 아래와 같은 구조의 이벤트 객체를 발행한다.

```java
/**
 * Def. 로그인 성공 이벤트
 *
 * @param userId - 사용자 id
 * @param refreshToken - 발행한 리프레시 토큰
 * @param remainedRefreshToken - nullable, 로그인 시도시 존재하고 있는 리프레시 토큰(삭제대상)
 */
public record LoginSuccessEvent(UUID userId, String refreshToken, String remainedRefreshToken) { }
```

- 해당 이벤트를 받아 후처리를 진행하고 싶다면 아래와 같이 @EventListener 선언하여 로그인 성공 정보를 받을 수 있다.

```java

@EventListener
public void handle(LoginSuccessEvent loginSuccessEvent) { ...}
```

4. 로그인 실패 후처리

- 1번 항목에서 설정한 로그인 실패 콜백 url로 로그인 요청 사용자의 request를 전송하니 아래와 같이 사용할 수 있다.

```java

@PostMapping("/loginFail")
public ResponseEntity<Map<String, Object>> loginFailProcess(HttpServletRequest request) { ...}
```

## 동작원리

auth 모듈의 인증과 인가 처리에 사용되는 아이디-패스워드 방식과 jwt 토큰 모두 SpringSecurity를 통해 구현하였으며  
Spring Security 공식문서의 기본 방식을 그대로 차용하고 네이밍 규칙 또한 그대로 차용하였다.  
(참고, jwt는 스프링 시큐리티의 공식 가이드는 없지만 아이디-패스워드 방식에서 사용하는 패턴과 네이밍 규칙을 그대로 사용하였다.)

### 각 구현체의 역할

- Filter :
- AuthenticationToken :
- Manager :
- Provider