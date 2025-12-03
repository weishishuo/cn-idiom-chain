# 成语接龙游戏 (Idiom Chain Game)

一个基于 Spring Boot 和 TypeScript 的 browser-server 架构成语接龙游戏。

## 功能特性

- 🎮 **成语接龙**：系统内置 50 个四字成语，支持连续接龙
- 💬 **聊天界面**：类似微信聊天的对话框形式，左侧系统回答，右侧用户输入
- 🎨 **圆角设计**：现代化的圆角界面风格
- 🔄 **重新开始**：随时可以重新开始游戏
- 📱 **响应式**：支持移动端和桌面端

## 技术栈

### 后端
- **Spring Boot 3.2.0**：Java 21+
- **Spring Data JPA**：数据持久化
- **PostgreSQL 13+**：数据库
- **Maven 3.9+**：构建工具

### 前端
- **TypeScript**：类型安全的 JavaScript
- **Vite**：快速的前端构建工具
- **CSS3**：现代化样式设计

## 数据库配置

### PostgreSQL 连接信息
- **地址**：`jdbc:postgresql://192.168.211.4:45433/idiom_chain`
- **用户名**：`weishishuo`
- **密码**：`password`

### 初始化脚本

数据库初始化脚本位于 `src/main/resources/data.sql`，包含 50 个四字成语。

## 安装和运行

### 1. 准备工作

- 确保安装了 Java 21+
- 确保安装了 Maven 3.9+
- 确保安装了 Node.js 22+
- 确保 PostgreSQL 13+ 数据库已启动

### 2. 后端运行

```bash
# 编译并运行后端
mvn spring-boot:run
```

后端服务将在 `http://localhost:8081` 启动。

### 3. 前端运行

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务将在 `http://localhost:3000` 启动。

## API 接口

### 获取下一个接龙成语

**POST** `/api/idiom/next`

**请求体**：
```json
{
  "userInput": "用户输入的成语",
  "previousIdiom": "上一个成语（可选）"
}
```

**响应**：
```json
{
  "success": true,
  "idiom": "下一个接龙成语",
  "message": "接龙成功"
}
```

**错误响应**：
```json
{
  "success": false,
  "message": "错误信息"
}
```

## 游戏规则

1. 系统首先发送欢迎词："欢迎玩成语接龙游戏，请输入一个四字成语"
2. 用户输入四字成语后，系统会：
   - 校验是否为四字成语
   - 校验成语是否在库中
   - 如果有上一个成语，校验是否可以接龙
   - 返回下一个接龙成语或错误信息
3. 点击"重新开始"按钮可以重置游戏

## 项目结构

```
cn-idiom-chain/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── idiomchain/
│   │   │               ├── IdiomChainApplication.java
│   │   │               ├── entity/
│   │   │               │   └── Idiom.java
│   │   │               ├── repository/
│   │   │               │   └── IdiomRepository.java
│   │   │               ├── service/
│   │   │               │   └── IdiomService.java
│   │   │               └── controller/
│   │   │                   └── IdiomController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── styles.css
│   └── main.ts
├── index.html
├── vite.config.ts
├── tsconfig.json
├── package.json
└── pom.xml
```

## 开发说明

- 后端端口：8081
- 前端端口：3000
- 跨域已配置：允许 `http://localhost:3000` 访问后端
- 后端不保存接龙历史，每次请求独立处理

## 许可证

MIT License