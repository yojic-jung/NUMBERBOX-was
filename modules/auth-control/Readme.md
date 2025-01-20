# Auth 모듈(인증, 인가 모듈)

## 개요

- auth 모듈은 로그인 인증 처리와 인증된 사용자에게 자원 접근을 허용하는 인가 처리를 진행한다.
- 인증, 인가 처리에는 두가지 방식이 존재한다.
    - 아이디-패스워드
    - jwt 토큰

## 사용법

### 1. config 설정

- auth 모듈을 의존하는 모듈에서 아래 설정을 진행한다.
- 각 항목 속성에 대한 설명은 주석 참고

```yml
kamcci:
  auth:
    login:
      url:
        process: /login/general  # 로그인 요청 uri
        fail: /login/fail # 로그인 실패시 콜백 uri
        logout: /logout # 로그아웃 요청 uri
    jwt:
      secretKey: localSecret # jwt 토큰 시크릿 키 값
      email: email # accessToken payload의 email로 사용될 claim 속성명
      id: userId # accessToken payload의 id로 사용될 claim 속성명
      domain: nsoohak.com # accessToken에 주입될 domain 값
      issuer: nsoohak # accessToken payload 표준 claim issuer 값
      audience: user # accessToken payload 표준 claim audience 값
      access-token:
        subject: nsoohakAccessToken # accessToken payload 표준 claim subject 값
        validTime: 3600000 # 토큰 유효시간 ms 단위
      refresh-token:
        subject: nsoohakRefreshToken # refreshToken payload 표준 claim subject 값
        validTime: 21600000 # 토큰 유효시간 ms 단위
        keepValidTime: 2592000000 # 토큰 유효시간 ms 단위(자동 로그인 요청한 경우)
``` 

### 2. 커스텀 속성 지정

- AuthConstantConfig.java 파일에서 아래와 같은 상수 값을 직접 지정할수 있다.
    - ex) 권한 prefix, 자동 로그인 request 요청 파라미터 속성, 리프레시 토큰이 담기는 쿠키이름

```java

public class AuthConstantConfig {
    // 시큐리티 권한 관리 role prefix
    public static final String ROLE_PREFIX = "ROLE_";
    /**
     * * 로그인 유지 요청 request 속성명
     * - 속성값이 LOGIN_KEEP_VAL인 경우 REFRESH_TOKEN_VALID_TIME_OP_KEEP으로 로그인 유지 시간 결정
     * - 속성값이 LOGIN_KEEP_VAL이 아닌 경우 REFRESH_TOKEN_VALID_TIME으로 로그인 유지 시간 결정
     */
    public static final String LOGIN_KEEP_ATTR = "loginState";
    // 로그인 유지 요청 속성값
    public static final String LOGIN_KEEP_VAL = "keep";
    // 클라이언트에 전달할 액세스 토큰 속성명
    public static final String ACCESS_TOKEN_NAME = "Authorization";
    public static final String TOKEN_STANDARD_PREFIX = "Bearer";
    // 클라이언트에 전달할 리프레시 토큰 속성명
    public static final String REFRESH_TOKEN_NAME = "refresh-token";
    // 클라이언트에 전달할 사용자 권한 속성명
    public static final String ROLE_NAME = "roles";

    private AuthConstantConfig() { }
}
```

### 3. 인증 정보 제공 인터페이스 구현

- 로그인 요청시 클라이언트가 전달한 인증 정보와 서버에서 저장한 인증정보를 비교해야하므로  
  아래 인터페이스를 auth 모듈을 사용하는 모듈에서 구현하여 서버측 인증 정보를 제공해야한다.

```java
public interface LoginRequestUserDetailService {
    AuthUserInfo loadUserByUsername(String username);

}
```

- loadUserIdByRefreshToken : 리프레시 토큰의 소유자 id를 반환해야한다.
    - auth 모듈 내부에서 액세스 토큰과 리프레스 토큰의 소유자가 같은지 체크함
- canReCreateRefreshToken : 리프레시 토큰이 만료 되었을 때, 해당 userId의 refreshToken 재발급 여부를 boolean 타입으로 반환해야한다.

```java
public interface JwtRequestUserDetailService {
    // 사용자 인증정보 반환
    UUID loadUserIdByRefreshToken(String token);

    // 사용자 리프레시 토큰 재발급 가능 여부 결정
    boolean canReCreateRefreshToken(UUID userId);
}

```

### 4. 로그인 성공 후처리

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

### 5. 로그인 실패 후처리

- [1. config 설정] 항목에서 설정한 로그인 실패 콜백 uri로 로그인 요청 사용자의 request를 전송하니  
  해당 uri를 요청 받는 컨트롤러를 구현하여 실패 후처리를 직접 진행할 수 있다.

```java
request.setAttribute("auth.error.exception",exception);

// authLoginUrlProperty.fail() 은 사용자가 config로 설정한 login 실패 콜백 uri
RequestDispatcher dispatcher = request.getRequestDispatcher(authLoginUrlProperty.fail());
dispatcher.

forward(request, response);
```

## 구현상세

auth 모듈의 인증과 인가 처리에 사용되는 아이디-패스워드 방식과 jwt 토큰 모두 SpringSecurity를 통해 구현하였으며  
Spring Security 공식문서의 기본 방식을 그대로 차용하고 네이밍 규칙 또한 그대로 차용하였다.  
(참고, jwt는 스프링 시큐리티의 공식 가이드는 없지만 username-password 방식에서 사용하는 패턴과 네이밍 규칙을 그대로 사용하였다.)

### 각 구현체의 역할

- Filter : 사용자 인증정보를 request에서 추출하여 manager에게 전달함
- AuthenticationToken : 인증정보를 담은 토큰으로 로그인 인증정보, jwt 토큰 인증정보를 담은 두 인증 토큰 존재
- Manager : AuthenticationToken에 따라 알맞은 provider에게 할당함
- Provider : 각 AuthenticationToken을 처리하는 provider가 존재하면 AuthenticationToken에서 사용자 인증정보 추출하여 인증 성공/실패 여부를 결정함

## 기타

### 부가기능

1. UserEmail, UserId, UserRole 어노테이션을 컨트롤러 메서드 input값에 설정시 accessToken에서 해당 정보 추출하여 사용자정보 주입
    - UserEmail은 String 타입
    - UserId은 UUID 타입
    - UserRole은 List<String> 타입
2. AuthPasswordEncoder를 통해 auth 모듈에서 사용하는 암호화 기능을 제공함
    - boolean matches(CharSequence rawPassword, String encodedPassword) : rawString 값과 암호화 값 비교
    - String encode(CharSequence rawPassword) : auth모듈에서 사용하는 암호화 방식으로 암호화

### 참고

- /public으로 시작하는 요청은 인증 필터를 거치지 않음
- 내부에서 처리되지 않는 예외의 경우 /error 요청으로 전달되도록 설정되어있으나, 현재 모든 예외 catch하여 실패 핸들러를 타도록 제공되고 있음
    - login(username, password) 필터의 경우 로그인 실패 콜백 uri로 모든 예외 정보 전달함
    - jwt 인증 필터의 경우, 예외 발생시 에러 응답 반환