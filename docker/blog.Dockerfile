# 构建阶段
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY backend/pom.xml .
# 先下载依赖（利用缓存层加速构建）
RUN mvn dependency:go-offline -B
COPY backend/src ./src
# 构建应用
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:17-jdk-slim
WORKDIR /app
# 复制构建产物
COPY --from=build /app/target/*.jar app.jar
# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# 安全增强
RUN adduser --system --group spring
USER spring:spring
# 启动配置
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]