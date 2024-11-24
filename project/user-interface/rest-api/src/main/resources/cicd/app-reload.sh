#!/bin/bash

# 8080 포트가 사용 중인지 확인
if lsof -i :8080 > /dev/null; then
    # 8080 포트가 사용 중일 경우
    GREEN_PORT=8081
    BLUE_PORT=8080
else
    # 8080 포트가 사용 중이지 않을 경우
    GREEN_PORT=8080
    BLUE_PORT=8081
fi

# 백업 서버 실행
nohup java -jar *.jar -DSpring.profiles.active=local --server.port=$GREEN_PORT 1> /dev/null 2>&1 &

# 백업 서버가 준비되었는지 확인 (5초 동안 15번 시도)
for i in {1..15}
do
  echo "$i 번째 GREEN 서버 헬스 $GREEN_PORT 체크 시도"

  # GREEN 서버가 준비되었는지 확인
  curl --silent --fail --head http://127.0.0.1:$GREEN_PORT/myWasApi/public/health

  if [ $? -eq 0 ]; then
    # Nginx 설정 파일에서 GREEN 서버로 변경
    sed -i '' "s/server 127.0.0.1:$BLUE_PORT;/server 127.0.0.1:$GREEN_PORT;/" /usr/local/etc/nginx/nginx.conf

    # Nginx 재로드 (변경된 설정 반영)
    nginx -s reload
    break
  fi

  sleep 5.0
done

kill -9 $(lsof -t -i:$BLUE_PORT)
