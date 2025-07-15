redis master/slave with sentinel 구성

1. redis docker-compose 실행

```bash
docker compose -f docker-compose-redis-master-slave.yml up -d  
```

2. redis master 접속

```bash
docker exec -it redis-master bash
```

3. master에서 master/slave 구성 확인

```bash
redis-cli -p 6379 INFO replication
```

4. master 제거  
   <br/>
5. slave에서 master 승격여부 확인
```bash
redis-cli -p 6380 info replication
```
```bash
redis-cli -p 6381 info replication
```