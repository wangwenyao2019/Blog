# 构建阶段
FROM node:18-alpine AS build
WORKDIR /app
COPY frontend/package*.json ./
RUN npm ci --silent
COPY frontend .
RUN npm run build --prod

# 运行阶段
FROM nginx:alpine
# 复制构建产物
COPY --from=build /app/dist/frontend /usr/share/nginx/html
# 自定义nginx配置
COPY frontend/nginx.conf /etc/nginx/conf.d/default.conf
# 健康检查
HEALTHCHECK --interval=30s --timeout=3s CMD wget --spider http://localhost || exit 1
EXPOSE 80