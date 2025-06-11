# 使用Java基礎鏡像
FROM maven:3.9.8-amazoncorretto-21

# 設置工作目錄
WORKDIR /app

# 複製Maven/Gradle構建文件
COPY pom.xml ./
COPY liquibase-docker.properties ./liquibase.properties
# 複製源代碼
COPY src ./src

COPY target/cv-user-service-1.0-SNAPSHOT.jar app.jar

# 暴露應用端口
EXPOSE 8080

# 啟動腳本
COPY start.sh ./
RUN chmod +x start.sh

CMD ["./start.sh"]