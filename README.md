# 成语接龙游戏

一个基于browser-server架构的成语接龙程序，使用Spring Boot和TypeScript技术栈开发。

## 功能特性

1. 系统内置一个成语库，提供初始化SQL脚本
2. 页面提示用户输入成语，后端返回下一个成语
3. 展示整个接龙过程
4. 如果成语库中找不到下一个成语，提示用户游戏结束
5. 后端不保存接龙历史，只提供获取下一个成语的API

## 技术栈

### 后端
- Spring Boot 3.2.0
- Java 21+
- Maven 3.9+
- PostgreSQL 13+

### 前端
- React 18.2.0
- TypeScript 5.2.2
- Vite 5.0.8
- Axios 1.6.0

## 项目结构

```
cn-idiom-chain-1/
├── backend/                 # 后端Spring Boot项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/idiomchain/
│   │   │   │   ├── controller/       # 控制器层
│   │   │   │   ├── service/           # 服务层
│   │   │   │   ├── repository/        # 数据访问层
│   │   │   │   ├── entity/            # 实体类
│   │   │   │   └── common/            # 公共类
│   │   │   └── resources/
│   │   │       ├── application.properties  # 配置文件
│   │   │       └── init_database.sql       # 数据库初始化脚本
│   │   └── test/
│   └── pom.xml             # Maven配置文件
├── frontend/                # 前端TypeScript项目
│   ├── src/
│   │   ├── App.tsx        # 主应用组件
│   │   └── main.tsx       # 应用入口
│   ├── index.html          # HTML入口文件
│   ├── vite.config.ts      # Vite配置文件
│   ├── tsconfig.json       # TypeScript配置文件
│   ├── tsconfig.node.json  # TypeScript Node配置文件
│   ├── package.json         # 项目依赖配置
│   └── .gitignore          # Git忽略文件
└── README.md                # 项目说明文档
```

## 快速开始

### 1. 环境准备

确保你已经安装了以下软件：
- JDK 21+
- Maven 3.9+
- PostgreSQL 13+
- Node 22+

### 2. 数据库配置

1. 创建一个名为`idiom_chain`的数据库：
   ```sql
   CREATE DATABASE idiom_chain;
   ```

2. 执行数据库初始化脚本`backend/src/main/resources/init_database.sql`，创建表并插入初始成语数据。

### 3. 后端服务启动

1. 进入后端项目目录：
   ```bash
   cd backend
   ```

2. 启动后端服务：
   ```bash
   mvn spring-boot:run
   ```

后端服务将在`http://localhost:8080`启动。

### 4. 前端开发服务器启动

1. 进入前端项目目录：
   ```bash
   cd frontend
   ```

2. 启动前端开发服务器：
   ```bash
   npm run dev
   ```

前端开发服务器将在`http://localhost:3000`启动。

### 5. 访问应用

打开浏览器访问`http://localhost:3000`，即可开始玩成语接龙游戏。

## API接口

### 1. 获取下一个成语

**接口地址**：`GET /api/idiom-chain/next?lastCharacter={lastCharacter}`

**参数说明**：
- `lastCharacter`：上一个成语的最后一个字

**返回示例**：
```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "content": "一帆风顺",
    "pinyin": "yī fān fēng shùn",
    "meaning": "船挂着满帆顺风行驶。比喻非常顺利，没有任何阻碍。",
    "firstCharacter": "一",
    "lastCharacter": "顺"
  }
}
```

### 2. 验证成语

**接口地址**：`GET /api/idiom-chain/validate?idiom={idiom}`

**参数说明**：
- `idiom`：要验证的成语

**返回示例**：
```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "content": "一帆风顺",
    "pinyin": "yī fān fēng shùn",
    "meaning": "船挂着满帆顺风行驶。比喻非常顺利，没有任何阻碍。",
    "firstCharacter": "一",
    "lastCharacter": "顺"
  }
}
```

## 构建部署

### 后端构建

```bash
cd backend
mvn clean package
```

构建完成后，将生成`target/idiom-chain-backend-1.0.0.jar`文件。

### 前端构建

```bash
cd frontend
npm run build
```

构建完成后，将生成`dist`目录，包含所有前端静态资源。

## 开发说明

### 后端开发

- 使用Spring Boot 3.2.0框架
- 使用Spring Data JPA访问数据库
- 使用Lombok简化代码
- 使用PostgreSQL数据库

### 前端开发

- 使用React 18.2.0框架
- 使用TypeScript进行类型检查
- 使用Vite作为构建工具
- 使用Axios进行HTTP请求

## 贡献

欢迎提交Issue和Pull Request！

## 许可证

MIT License
