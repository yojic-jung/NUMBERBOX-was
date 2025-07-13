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
  host.docker.internal:7001 \
  host.docker.internal:7002 \
  host.docker.internal:7003 \
  host.docker.internal:7004 \
  host.docker.internal:7005 \
  host.docker.internal:7006 \
  --cluster-replicas 1
```

4. redis cluster 구성 확인

```bash
redis-cli -p 7001 cluster info
```

```bash
redis-cli -p 7001 cluster slots
```
