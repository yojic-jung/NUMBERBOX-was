# logging 모듈

## 개요

- logging 모듈은 사용자의 api 요청-응답 정보를 로깅한다.
- 로깅 정보는 event로 발행되며 아래와 같다.
    - 회원 id, browser, os, ip, http method, uri, request body, response code

## 사용법

### 1. config 설정

- logging 모듈을 의존하는 모듈에서 아래 설정을 진행한다.
- 각 항목 속성에 대한 설명은 주석 참고

```yml
kamcci:
  logging:
    request:
      content-type: # 해당 content-type에 대해서만 로깅 진행
        - application/json
      exceptUri: # 해당 uri의 경우 로깅 제외
        - /file/hwp
        - /file/img
      body-except-uri: # 해당 uri의 경우 request body 정보는 null로 반환
        - /member/phone
        - /public/member/findEmail
        - /public/member/signup
        - /member/password
        - /member/password-confirm
``` 

### 2. 로깅 정보 이벤트 수신

- 로깅 이벤트 객체는 아래와 같이 이루어져 있다.

```kotlin
/**
 * 클라이언트 http 요청 및 응답 로깅 정보
 */
data class ClientLoggingInfoEventDto(
    val reqLoggingDto: HttpRequestLoggingDto,
    val resLoggingDto: HttpResponseLoggingDto,
)

/**
 * 클라이언트 http 요청 로깅 정보
 */
data class HttpRequestLoggingDto(
    val memberId: UUID,
    val browser: String,
    val os: String,
    val ip: String,
    val method: String,
    val uri: String,
    val reqBody: String?
)

/**
 * 클라이언트 http 요청에 대한 서버 응답 로깅 정보
 */
data class HttpResponseLoggingDto(
    val httpStatus: Int
)
```

- 로깅 정보 이벤트 객체를 수신하는 곳에서는 @EventListener 선언하여 수신할 수 있다.

```kotlin
@Async
@EventListener
fun handle(loggingEventDto: ClientLoggingInfoEventDto) {
    logClientApiRepository.save(loggingEventDto)
}
```

## 구현상세

- 로깅 모듈은 aop를 통해 구현하였으며 포인트컷은 컨트롤러에 아래 어노테이션이 적용된 메서드이다.
    - RequestMapping, GetMapping, PostMapping, PatchMapping, PutMapping, DeleteMapping
- request 정보는 필터를 거쳐서 전달되는 HttpServletReqeust에서 추출한다.
    - request body는 한번 읽어들이면 다시 읽을 수 없다.  
      -> request를 래핑한 ContentCachingRequestWrapper는 request body 캐싱 가능  
      -> ContentCachingRequestWrapper로 request를 래핑하는 필터를 필터 체인 마지막에 등록  
      -> 이를 통해 컨트롤러 메서드 실행 이전에 aop 프록시가 request body를 읽어들여도 컨트롤러에서 다시 읽어들일 수 있음
- response code는 컨트롤러 메서드 응답객체에서 추출한다.
    - 응답객체는 ResponseEntity타입에 대해서만 추출 가능함
    - ResponseEntity타입 아닌 경우 null 반환
