source /env.sh
PROJECT_ROOT="/home/ubuntu/app/server"

ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

BUILD_JAR="$PROJECT_ROOT/build/libs/reservation-0.0.1-SNAPSHOT.jar"
JAR_NAME=$(basename "$BUILD_JAR")

echo "$TIME_NOW > $JAR_NAME 파일 복사" >> "$DEPLOY_LOG"
cp "$BUILD_JAR" "$PROJECT_ROOT/"

echo "> 현재 실행중인 애플리케이션 pid 확인" >> "$DEPLOY_LOG"
CURRENT_PID=$(pgrep -f "$JAR_NAME")

if [ -z "$CURRENT_PID" ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> "$DEPLOY_LOG"
else
  echo "> kill -15 $CURRENT_PID" >> "$DEPLOY_LOG"
  kill -15 "$CURRENT_PID"
  sleep 5
fi

DEPLOY_JAR="$PROJECT_ROOT/$JAR_NAME"

echo "$TIME_NOW > $DEPLOY_JAR 파일 실행" >> "$DEPLOY_LOG"
nohup java -DDB_USERNAME="$DB_USERNAME" -DDB_PASSWORD="$DB_PASSWORD" -DREDIS_PASSWORD="$REDIS_PASSWORD" -jar "$DEPLOY_JAR" >> "$DEPLOY_LOG" 2>>"$ERROR_LOG" &
echo "$TIME_NOW > $DEPLOY_JAR 파일 실행 완료" >> "$DEPLOY_LOG"
