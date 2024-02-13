PROJECT_ROOT="/home/ubuntu/app/server"
JAR_FILE="$PROJECT_ROOT/reservation-0.0.1-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

echo "$TIME_NOW > $JAR_FILE 파일 복사" >> "$DEPLOY_LOG"
cp "$PROJECT_ROOT/build/libs/reservation-0.0.1-SNAPSHOT.jar" "$PROJECT_ROOT/"

echo "$TIME_NOW > $JAR_FILE 파일 실행" >> "$DEPLOY_LOG"
DEPLOY_PATH="/home/ubuntu/app/server/"
BUILD_JAR=$(ls /home/ubuntu/app/server/build/libs/reservation-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename "$BUILD_JAR")

echo "> build 파일명: $JAR_NAME" >> "$DEPLOY_LOG"

echo "> build 파일 복사" >> "$DEPLOY_LOG"
cp "$BUILD_JAR" "$DEPLOY_PATH"

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

DEPLOY_JAR="${DEPLOY_PATH}/${JAR_NAME}"
echo "> DEPLOY_JAR 배포" >> "$DEPLOY_LOG"
nohup java -DDB_USERNAME="$DB_USERNAME" -DDB_PASSWORD="$DB_PASSWORD" -jar "$DEPLOY_JAR" >> "$DEPLOY_LOG" 2>>"$ERROR_LOG" &
