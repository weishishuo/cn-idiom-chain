# 成语接龙游戏 (Chinese Idiom Chain Game)

一个基于 Spring Boot 和 TypeScript 的成语接龙游戏，采用 Browser-Server 架构。

## 功能特点

- 🎮 **成语接龙游戏**：用户输入四字成语，系统自动接龙
- 📚 **内置成语库**：包含 50 个常用四字成语
- 💬 **聊天界面**：类似微信的对话框形式，用户消息在右，系统消息在左
- 🔄 **重新开始**：随时可以重置游戏
- 🎨 **现代化UI**：圆角设计，渐变背景，流畅动画

## 技术栈

### 后端
- **Spring Boot 3.3.0** - Java Web 框架
- **Spring Data JPA** - ORM 框架
- **PostgreSQL 13+** - 数据库
- **Java 21** - 开发语言
- **Maven 3.9+** - 构建工具

### 前端
- **React 18** - UI 框架
- **TypeScript** - 开发语言
- **Vite** - 构建工具
- **Axios** - HTTP 客户端

## 项目结构

```
cn-idiom-chain-1/
├── src/                     # 后端 Java 代码
│   ├── main/
│   │   ├── java/com/example/idiomchain/
│   │   │   ├── controller/  # 控制器
│   │   │   ├── entity/      # 实体类
│   │   │   ├── repository/  # 数据访问层
│   │   │   ├── service/     # 服务层
│   │   │   └── IdiomChainApplication.java  # 应用主类
│   │   └── resources/
│   │       └── application.yml  # 配置文件
├── frontend/                # 前端代码
│   ├── src/
│   │   ├── components/      # 组件
│   │   ├── services/        # API 服务
│   │   ├── types/           # TypeScript 类型定义
│   │   ├── App.tsx          # 主应用组件
│   │   ├── main.tsx         # 应用入口
│   │   └── index.css        # 全局样式
│   ├── index.html           # HTML 模板
│   ├── package.json         # 前端依赖
│   ├── tsconfig.json        # TypeScript 配置
│   └── vite.config.ts       # Vite 配置
├── init.sql                 # 数据库初始化脚本
├── pom.xml                  # Maven 配置
└── README.md                # 项目说明
```

## 数据库配置

### 1. 创建数据库

```sql
CREATE DATABASE idiom_chain;
```

### 2. 执行初始化脚本

运行 `init.sql` 文件，创建 `idioms` 表并插入 50 个成语数据。

### 3. 配置文件

数据库连接信息已在 `src/main/resources/application.yml` 中配置：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://192.168.211.4:45433/idiom_chain
    username: weishishuo
    password: password
```

## 运行项目

### 1. 启动后端服务

```bash
# 进入项目根目录
cd cn-idiom-chain-1

# 编译并运行 Spring Boot 应用
mvn spring-boot:run
```

后端服务将在 `http://localhost:8081` 启动。

### 2. 启动前端服务

```bash
# 进入前端目录
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务将在 `http://localhost:3000` 启动，并自动打开浏览器。

## API 接口

### 获取下一个成语

**接口地址**：`POST /api/idiom/next`

**请求参数**：
```json
{
  "userInput": "成语内容",
  "previousIdiom": "上一个成语（可选）"
}
```

**响应示例**：

成功：
```json
{
  "success": true,
  "idiom": "接龙成语",
  "meaning": "成语含义"
}
```

失败：
```json
{
  "success": false,
  "message": "错误信息"
}
```

## 游戏规则

1. 系统首先发送欢迎消息："欢迎玩成语接龙游戏，请输入一个四字成语"
2. 用户输入四字成语后，系统进行以下校验：
   - 成语是否在成语库中
   - 如果有上一个成语，当前输入是否能接龙（最后一个字与上一个成语的第一个字相同）
3. 校验通过后，系统返回下一个接龙的成语
4. 如果找不到接龙成语，系统提示："成语没有下一个接龙"
5. 用户可以随时点击"重新开始"按钮重置游戏

## 开发说明

- 后端使用 Spring Boot 3.3.0，需要 Java 21+ 环境
- 前端使用 React 18 + TypeScript + Vite，需要 Node.js 22+ 环境
- 数据库使用 PostgreSQL 13+，请确保数据库服务已启动
- 后端已配置跨域支持，允许来自 `http://localhost:3000` 的请求

## License

MIT
