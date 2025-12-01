# 成语接龙游戏

一个基于Spring Boot和TypeScript的成语接龙应用程序。

## 技术栈

### 后端
- Spring Boot 3.2.0
- Java 21
- PostgreSQL 13+
- Maven 3.9+

### 前端
- React 18.2.0
- TypeScript 5.3.0
- Vite 5.0.0
- Axios 1.6.0

## 功能特性

1. **成语库**：系统内置丰富的成语数据
2. **成语接龙**：用户输入成语，系统自动返回下一个成语
3. **接龙验证**：验证用户输入的成语是否有效，以及是否符合接龙规则
4. **游戏过程展示**：页面实时展示整个接龙过程
5. **错误提示**：当找不到下一个成语或用户输入无效时，显示相应的错误提示

## 项目结构

```
cn-idiom-chain/
├── frontend/                 # 前端项目
│   ├── src/                 # 前端源代码
│   │   ├── App.tsx         # 主应用组件
│   │   └── main.tsx        # 应用入口
│   ├── index.html           # HTML模板
│   ├── package.json         # 前端依赖配置
│   ├── tsconfig.json        # TypeScript配置
│   ├── tsconfig.node.json   # TypeScript Node配置
│   └── vite.config.ts       # Vite配置
├── src/                      # 后端源代码
│   └── main/                # 主代码目录
│       ├── java/            # Java源代码
│       │   └── com/         # 包名
│       │       └── idiom/   # 项目包名
│       │           └── chain/ # 项目模块名
│       │               ├── controller/ # 控制器层
│       │               ├── entity/      # 实体类
│       │               ├── repository/   # 数据访问层
│       │               ├── service/      # 服务层
│       │               └── IdiomChainApplication.java # 应用启动类
│       └── resources/        # 资源文件
│           ├── application.properties # 应用配置
│           └── data.sql               # 数据库初始化脚本
├── pom.xml                    # 后端Maven配置
└── README.md                  # 项目说明文档
```

## 快速开始

### 1. 环境准备

确保以下环境已安装：
- Java 21+
- Maven 3.9+
- PostgreSQL 13+
- Node 22+
- npm 10+

### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE idiom_chain;
```

2. 修改 `src/main/resources/application.properties` 中的数据库连接信息：
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/idiom_chain
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. 启动后端服务

在项目根目录下执行以下命令：

```bash
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动。

### 4. 启动前端服务

在 `frontend` 目录下执行以下命令：

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务将在 `http://localhost:3000` 启动。

### 5. 访问应用

在浏览器中访问 `http://localhost:3000` 即可开始游戏。

## API接口

### 1. 验证成语

```http
GET /api/idiom/validate/{content}
```

**参数**：
- `content`：要验证的成语内容

**响应**：
```json
{
  "valid": true
}
```

### 2. 获取下一个成语

```http
GET /api/idiom/next/{lastIdiom}
```

**参数**：
- `lastIdiom`：最后一个成语内容

**响应**：
```json
{
  "id": 1,
  "content": "一帆风顺",
  "pinyin": "yī fān fēng shùn",
  "meaning": "船挂着满帆顺风行驶。比喻非常顺利，没有任何阻碍。"
}
```

### 3. 获取随机初始成语

```http
GET /api/idiom/random/start
```

**响应**：
```json
{
  "id": 2,
  "content": "二龙戏珠",
  "pinyin": "èr lóng xì zhū",
  "meaning": "两条龙相对，戏玩着一颗宝珠。"
}
```

## 游戏规则

1. 系统随机生成一个初始成语作为游戏开始
2. 用户需要输入一个成语，该成语的第一个字必须与上一个成语的最后一个字相同
3. 系统验证用户输入的成语是否有效，如果有效，则返回下一个成语
4. 重复步骤2和3，直到系统找不到下一个成语或用户输入无效

## 数据库初始化

项目启动时，会自动执行 `src/main/resources/data.sql` 脚本，初始化成语数据。

## 开发说明

### 后端开发

1. 实体类：`src/main/java/com/idiom/chain/entity/Idiom.java`
2. 数据访问层：`src/main/java/com/idiom/chain/repository/IdiomRepository.java`
3. 服务层：`src/main/java/com/idiom/chain/service/IdiomService.java` 和 `src/main/java/com/idiom/chain/service/impl/IdiomServiceImpl.java`
4. 控制器层：`src/main/java/com/idiom/chain/controller/IdiomController.java`

### 前端开发

1. 主应用组件：`frontend/src/App.tsx`
2. 应用入口：`frontend/src/main.tsx`

## 构建部署

### 后端构建

```bash
mvn clean package
```

构建完成后，生成的jar文件位于 `target/idiom-chain-1.0.0.jar`。

### 前端构建

```bash
cd frontend
npm run build
```

构建完成后，生成的静态文件位于 `frontend/dist` 目录。

### 部署

1. 部署后端jar文件：
```bash
java -jar target/idiom-chain-1.0.0.jar
```

2. 部署前端静态文件：
将 `frontend/dist` 目录下的文件部署到任何静态文件服务器（如Nginx、Apache等）。

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request。