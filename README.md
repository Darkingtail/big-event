# Big Event 后端服务

基于 Spring Boot 3 + MyBatis-Plus + MySQL + Redis 的后端示例项目，提供用户、分类、文章及文件上传等接口，响应统一封装为 `Result`。

**技术栈**
- Spring Boot 3.1.1、Spring Web、Validation
- MyBatis-Plus、MyBatis XML 映射
- MySQL 8.x、Redis 6+
- Lombok、Java JWT、Hutool
- 测试：JUnit 3/5（混合）

**运行环境**
- JDK 17+
- Maven 3.8+
- MySQL 与 Redis 本地可用实例

**快速开始**
- 数据库初始化：
  - 创建数据库：`CREATE DATABASE big_event DEFAULT CHARSET utf8mb4;`
  - 导入脚本：`db/big_event.sql`
- 配置连接：编辑 `src/main/resources/application.yml`
  - `spring.datasource.url`: `jdbc:mysql://127.0.0.1:3306/big_event`
  - `spring.datasource.username` / `spring.datasource.password`
  - `spring.data.redis.host` / `spring.data.redis.port`
  - `token`: 请求头名称（默认 `Authorization`）
  - 生产环境请通过环境变量覆盖上述配置（示例：`SPRING_DATASOURCE_URL`、`SPRING_DATASOURCE_USERNAME`、`SPRING_DATASOURCE_PASSWORD`、`SPRING_DATA_REDIS_HOST`）。
- 启动应用：
  - 开发运行：`mvn spring-boot:run`
  - 打包运行：`mvn clean package` → `java -jar target/big-event-1.0-SNAPSHOT.jar`
- 访问资源：
  - 示例页面：`http://localhost:8080/pages/index.html`
  - 上传文件静态访问：`http://localhost:8080/files/<文件名>`

**项目结构**
- `src/main/java/org/example`
  - `controller`：HTTP 接口（`/user`、`/category`、`/article`、`/upload`）
  - `service` / `service/impl`：业务服务与实现
  - `mapper`：持久层接口（映射 XML 见 `src/main/resources/com/example/mapper`）
  - `pojo`：实体与通用返回体（`Result`、`User`、`Category`、`Article` 等）
  - `utils`：`JwtUtil`、`Md5Util`、`ThreadLocalUtil`
  - `config`：`WebConfig`（拦截器链）、`StaticResourceConfig`（静态资源映射）、`MyBatisPlusConfig`（分页插件）
  - `interceptors`：`LoginInterceptor`（基于 Redis 的 Token 校验与 ThreadLocal 透传）
  - `exception`：`GlobalExceptionHandler`（统一异常处理）
- `src/main/resources`
  - `application.yml`：默认 MySQL/Redis 配置与 MyBatis(Plus) 设置
  - `static/`：静态资源与示例页面
- 其他
  - `db/big_event.sql`：数据库建表与示例数据
  - `docs/大事件接口文档.(md|pdf)`：接口文档

**API 约定**
- 统一响应：`Result<T> { code(0/1), message, data }`
- 认证：
  - 登录 `/user/login` 返回 JWT，默认放入响应体 `data`；同时把 Token 写入 Redis（示例工程默认 30 秒过期用于演示）。
  - 其后请求需在请求头携带 `${token}`（默认 `Authorization`）。
  - `LoginInterceptor` 会校验 Redis 中的 Token 并解析 JWT，将 claims 透传到 `ThreadLocalUtil`。
- 主要端点（简要）：
  - `/user/register`、`/user/login`、`/user/info`、`/user/update`、`/user/updateAavtar`、`/user/updatePwd`
  - `/category/detailByCategoryName`、`/category/detailById`、`/category/add`、`/category`（列表）
  - `/article`（POST 新增）、`/article/list`（GET 分页，支持 `pageNo/pageSize/categoryId/state`）
  - `/upload`（POST，`multipart/form-data`，字段名 `file`；返回相对路径 `files/<uuid>.<ext>`）
- 校验：基于 Bean Validation，自定义注解 `@State` 约束文章状态为“已发布”或“草稿”。

**静态资源与文件上传**
- 上传目录：项目根目录下 `files/`（自动创建）
- 访问映射：`/files/**` → `<project-root>/files`，`/pages/**` → `classpath:/static/pages/`

**分页与 MyBatis-Plus**
- 已启用分页插件（最大单页 `1000`）。
- `ArticleController` 演示了基于条件的分页与 `PageDTO` 返回结构。

**测试**
- 位置：`src/test/java`
- 运行：`mvn test`
- 说明：包含 Redis/JWT 示例测试；运行 Redis 相关测试需本地 Redis 可用。

**开发与提交规范**
- 包结构：控制层 `controller`，业务层 `service/impl`，持久层 `mapper`，DTO/BO/实体在 `pojo`，工具在 `utils`。
- 编码风格：4 空格缩进，类 `UpperCamelCase`，方法/字段 `lowerCamelCase`，常量 `UPPER_SNAKE_CASE`。
- Web 路由：控制器使用 `@RequestMapping`，在 `/user` 等前缀下组织端点。
- 响应封装：统一使用 `Result`。
- 提交信息：遵循 Conventional Commits（如 `feat(user): 支持头像更新`）。

**常用命令**
- `mvn clean package`：打包产物位于 `target/`
- `mvn package -DskipTests`：跳过测试打包（合并前请完整执行测试）
- `mvn spring-boot:run`：本地热更新开发

**配置与安全建议**
- 禁止将真实数据库凭据提交到仓库；通过环境变量或本地 profile 覆盖 `application.yml` 默认值。
- 保持 `.gitignore` 生效，避免提交 IDE 与构建产物。
- 生产使用前请调整 Token 过期时间、JWT 密钥与 Redis 过期策略。

**参考文档**
- `docs/大事件接口文档.md`

