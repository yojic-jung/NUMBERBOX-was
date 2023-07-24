# NUMBERBOX-was
> 위 프로젝트는 N명의수학 백엔드 프로젝트입니다.  
> N명의수학은 초중고 수학교육과정에 맞춤화된 수학컨텐츠 제작 및 공유 플랫폼입니다.

<br/>

## 개발기간
> **22.02 ~ 22.11(개발) : 8개월 간 웹서비스 구축 및 수학컨텐츠 제작**<br/> **22.11 ~ 23.07(운영) : 유지보수 및 기능 업데이트**
<br/>

## 시스템 구성(인프라)
<img src="https://github.com/yojic-jung/NUMBERBOX-was/assets/45252387/012b601b-3729-4c7f-af51-8fc40b746d1e" width="500" >  

<br/><br/>

## 실행가이드
> Back-end 프로젝트

1. java8 설치  
  
2. 깃을 통해 프로젝트 다운  
```
git clone https://github.com/yojic-jung/NUMBERBOX-was.git
```
  
3. 프로젝트 루트경로로 이동  
```
cd Numberbox-was
```
  
4. 빌드 후 실행  
```
./gradlew build
```
```
java -jar -Dspring.profiles.active=prod -Ddb_username=[DB아이디] -Ddb_passwd=[DB비밀번호] -Djwt_secret_key=[임의의 jwt시크릿키] -Ds3_bucket_name=[s3버킷이름] -Daws_s3_access_key=[s3액세스키] -Daws_s3_secret_key=[s3시크릿키] -Dopenai_secret_key=[openAi시크릿키] -Demail_address=[개인이메일] -Demail_password=[개인이메일비밀번호] $JARPATH/$JARNAME
```
<br/><br/>

## Environments
<img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"><img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"><img src="https://img.shields.io/badge/amazons3-1572B6?style=for-the-badge&logo=amazons3&logoColor=white"><img src="https://img.shields.io/badge/linux-FCC624?style=for-the-badge&logo=linux&logoColor=white">  

<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white"><img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"><img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white">  

<img src="https://img.shields.io/badge/mariadb-1F305F?style=for-the-badge&logo=mariadb&logoColor=white">


<br/><br/>

## 라이브러리
* spring-boot-starter-security
* commons-fileupload
* jsonwebtoken
* java-uuid-generator
* poi-ooxml
* iamport-rest-client-java
* javax.mail
* spring-cloud-starter-aws
* aws-java-sdk-s3
* KOMORAN
* qlrm
* retrofit
<br/><br/>

## 설정파일
* [build.gradle](https://github.com/yojic-jung/NUMBERBOX-was/blob/master/build.gradle)
* [application.properties](https://github.com/yojic-jung/NUMBERBOX-was/blob/master/src/main/resources/application.properties)
* [application-local.properties](https://github.com/yojic-jung/NUMBERBOX-was/blob/master/src/main/resources/application-local.properties)
* [SecurityConfig.java](https://github.com/yojic-jung/NUMBERBOX-was/blob/master/src/main/java/com/numberbox/config/SecurityConfig.java)

<br/><br/>

## 패키지 구조
```bash
├─.gradle
│  ├─7.3.2
│  ├─buildOutputCleanup
│  └─vcs-1
├─.logs
├─.settings
├─bin
│  ├─default
│  ├─main
│  │  ├─com
│  │  │  └─numberbox
│  │  └─logs
│  ├─querydsl
│  │  └─queryDsl
│  └─test
│      └─com
│          └─numberbox
├─build
│  └─tmp
│      └─compileQuerydsl
├─gradle
│  └─wrapper
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─numberbox
│  │  │          ├─aws
│  │  │          │  └─s3                //s3 설정 및 서비스 패키지
│  │  │          │      ├─config 
│  │  │          │      ├─dto
│  │  │          │      └─service
│  │  │          ├─common              //공통 서비스 패키지
│  │  │          │  ├─controller
│  │  │          │  ├─dto
│  │  │          │  ├─entity
│  │  │          │  ├─repository
│  │  │          │  ├─service
│  │  │          │  └─util
│  │  │          ├─config              //시큐리티 및 mvc설정 패키지
│  │  │          ├─convert
│  │  │          │  ├─controller
│  │  │          │  ├─dto
│  │  │          │  ├─entity
│  │  │          │  ├─repository
│  │  │          │  └─service
│  │  │          ├─iamport
│  │  │          ├─jwt                //jwt생성 및 관리 패키지
│  │  │          │  ├─entity
│  │  │          │  ├─repository
│  │  │          │  ├─service
│  │  │          │  └─util
│  │  │          ├─mathdocs          //학습지 제작 관련 패키지
│  │  │          │  ├─controller
│  │  │          │  ├─dto
│  │  │          │  ├─entity
│  │  │          │  ├─repository
│  │  │          │  └─service
│  │  │          ├─mathinfo          //수학문제 관련 패키지
│  │  │          │  ├─controller
│  │  │          │  ├─domain
│  │  │          │  ├─dto
│  │  │          │  ├─entity
│  │  │          │  ├─repository
│  │  │          │  └─service
│  │  │          ├─members            //회원 관련패키지
│  │  │          │  ├─controller
│  │  │          │  ├─domain
│  │  │          │  ├─dto
│  │  │          │  ├─entity
│  │  │          │  ├─repository
│  │  │          │  └─service
│  │  │          ├─scheduler          //스케줄러(배치잡 수행)
│  │  │          │  ├─job
│  │  │          │  └─service
│  │  │          ├─security          //시큐리티 서비스 및 핸들러 패키지
│  │  │          │  ├─dto
│  │  │          │  ├─handler
│  │  │          │  ├─service
│  │  │          │  └─util
│  │  │          ├─serivcecenter
│  │  │          │  └─entity
│  │  │          └─servicecenter
│  │  │              ├─controller
│  │  │              ├─dto
│  │  │              ├─repository
│  │  │              └─service
│  │  ├─resources
│  │  │  └─logs            //로그파일
│  │  │      ├─aws
│  │  │      ├─common
│  │  │      ├─convert
│  │  │      ├─jwt
│  │  │      ├─mathdocs
│  │  │      ├─mathinfo
│  │  │      ├─members
│  │  │      └─servicecenter
│  │  └─webapp
│  │      └─static        //정적파일
│  │          ├─contentsImg
│  │          ├─hwpToHtml
│  │          ├─imgFileDir
│  │          ├─nbImg
│  │          ├─profileImg
│  │          ├─resourceImg
│  │          ├─resourcePpt
│  │          ├─resourcePptImg
│  │          ├─solutionImg
│  │          ├─svcCenterImg
│  │          └─userHwp
│  └─test
│      └─java
│          └─com
│              └─numberbox
└─Users82108gitNUMBERBOX-wassrcmainwebapplogback
```
