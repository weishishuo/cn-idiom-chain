# 成语接龙游戏

基于 Spring Boot + TypeScript React 的浏览器-服务器架构成语接龙游戏。

## 技术栈要求

### 后端
- JDK 21+
- Maven 3.9+
- Spring Boot 3.3
- PostgreSQL 13+

### 前端
- Node.js 22+
- TypeScript 5.2+
- React 18.3+
- Vite 5.3+

## 项目结构

```
cn-idiom-chain-2/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── cnidiomchain/
│   │   │               ├── CnIdiomChainApplication.java   # 主启动类
│   │   │               ├── controller/                     # API控制器
│   │   │               ├── service/                        # 业务逻辑
│   │   │               ├── repository/                     # 数据访问
│   │   │               └── entity/                         # 实体类
│   │   └── resources/
│   │       ├── application.properties    # 配置文件
│   │       └── init.sql                 # 数据库初始化脚本
│   └── frontend/                        # 前端React项目
├── pom.xml                              # Maven配置
├── package.json                         # 前端依赖
├── tsconfig.json                        # TypeScript配置
└── vite.config.ts                       # Vite配置
```

## 快速开始

### 1. 数据库初始化

1. 创建PostgreSQL数据库
```bash
psql -U postgres
CREATE DATABASE idiom_db;
```

2. 执行初始化脚本
```bash\c idiom_db
\i src/main/resources/init.sql
```

3. 修改数据库连接配置

编辑 `src/main/resources/application.properties`，根据你的数据库配置修改以下内容：
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/idiom_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 2. 启动后端服务

```bash
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

### 3. 启动前端服务

```bash
npm install
npm run dev
```

前端页面将在 http://localhost:5173 启动

## API接口

### 获取下一个接龙成语

**POST** `/api/idiom/next`

请求体：
```json
{
  "idiom": "一帆风顺"
}
```

成功响应：
```json
{
  "idiom": "顺水推舟",
  "pinyin": "shùn shuǐ tuī zhōu"
}
```

无接龙成语响应：
```json
{
  "error": "找不到可以接龙的下一个成语"
}
```

### 验证成语是否存在

**POST** `/api/idiom/validate`

请求体：
```json
{
  "idiom": "一帆风顺"
}
```

响应：
```json
{
  "valid": true
}
```

## 功能说明

1. **成语接龙**：用户输入成语，系统自动返回下一个可以接龙的成语
2. **历史记录**：完整展示整个接龙过程，区分用户和系统输入
3. **格式验证**：自动验证输入的成语格式正确性
4. **接龙校验**：检查输入的成语是否符合接龙规则
5. **错误提示**：友好的错误提示信息
6. **拼音展示**：系统返回的成语附带拼音展示

## 内置成语库

项目内置了超过100个常用成语，可以直接使用。你可以通过修改 `init.sql` 文件添加更多成语。

## 跨域配置

后端已配置CORS跨域支持，允许前端 http://localhost:5173 访问接口。

## 开发说明

### 后端开发

- 使用 Spring Data JPA 操作数据库
- 使用 Lombok 简化实体类代码
- RESTful API 设计风格

### 前端开发

- 使用 TypeScript 保证类型安全
- 使用 React Hooks 管理状态
- 使用 Vite 作为构建工具，提供极速的开发体验
- 响应式设计，支持移动端访问

## License

MIT