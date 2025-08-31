## 모니터링 tool 사용법

1. mysql exporter 계정 생성

```
CREATE USER 'exporter'@'localhost' IDENTIFIED BY '1111';
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'exporter'@'localhost';
FLUSH PRIVILEGES;
```

2. my.cnf 파일 생성

```
[client]
user=exporter
password=1111
host=host.docker.internal
port=3306
```

3. prometheus.yml

```
- targets: [ "localhost:9090" ]     # 각 서버 ip로 설정
```

4. 도커 실행

```
docker-compose up -d
```

5. 대시보드 템플릿 참고

```
스프링 부트 : 19004
(* 위 19004는 connection-usage-time 지표가 정상적이지 않음)
스프링 부트(jdbc) : 6083
mysql : 14057
redis : 11835
```