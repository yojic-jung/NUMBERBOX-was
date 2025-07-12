redis-cluster 구성방법

1. redis docker-compose 실행

```bash
docker compose -f docker-compose-redis-cluster.yml up -d  
```

2. redis server 접속

```bash
docker exec -it redis1 bash
```

3. redis-client를 통한 클러스터 구성

```bash
redis-cli --cluster create \
  127.0.0.1:7001 \
  127.0.0.1:7002 \
  127.0.0.1:7003 \
  127.0.0.1:7004 \
  127.0.0.1:7005 \
  127.0.0.1:7006 \
  --cluster-replicas 1
```

4. redis cluster 구성 확인

```bash
redis-cli -p 7001 cluster info
```

```bash
redis-cli -p 7001 cluster slots
```
