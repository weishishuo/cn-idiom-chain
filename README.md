# 成语接龙应用 - 用户注册模块

基于Spring Boot框架开发的成语接龙应用，提供用户注册API接口。

## 功能特性

- ✅ 用户注册API
- ✅ 支持邮箱和手机号注册
- ✅ 数据验证和错误处理
- ✅ 密码加密存储
- ✅ RESTful API设计

## 技术栈

- **后端框架**: Spring Boot 3.2.0
- **数据库**: PostgreSQL 15+
- **ORM框架**: Spring Data JPA
- **密码加密**: BCrypt
- **数据验证**: Hibernate Validator
- **构建工具**: Maven

## 快速开始

### 1. 环境准备

- JDK 17+
- PostgreSQL 15+
- Maven 3.6+

### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE IF NOT EXISTS cn_idiom_chain WITH ENCODING 'UTF8' LC_COLLATE 'zh_CN.UTF-8' LC_CTYPE 'zh_CN.UTF-8';
```

2. 修改数据库连接配置（`src/main/resources/application.yml` 或 `application.properties`）：
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cn_idiom_chain?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 3. 运行项目

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

项目将在 `http://localhost:8080/api` 启动。

## API接口

### 用户注册

**接口地址**: `POST /users/register`

**请求参数**:
```json
{
  "username": "string",      // 用户名，3-50个字符
  "email": "string",        // 邮箱格式
  "phone": "string",        // 手机号格式：1[3-9]\d{9}
  "password": "string"      // 密码，6-20个字符
}
```

**请求示例**:
```json
{
  "username": "testuser",
  "email": "test@example.com",
  "phone": "13800138000",
  "password": "123456"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "注册成功",
  "data": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "phone": "13800138000",
    "createdAt": "2024-01-01T12:00:00"
  }
}
```

**错误响应示例**:
```json
{
  "code": 500,
  "message": "用户名已存在",
  "data": null
}
```

## 项目结构

```
cn-idiom-chain/
├── src/
│   ├── main/
│   │   ├── java/com/example/cnidiomchain/
│   │   │   ├── CnIdiomChainApplication.java  # 主启动类
│   │   │   ├── controller/                   # 控制器层
│   │   │   │   └── UserController.java       # 用户控制器
│   │   │   ├── service/                      # 服务层
│   │   │   │   ├── UserService.java          # 用户服务接口
│   │   │   │   └── impl/UserServiceImpl.java # 用户服务实现
│   │   │   ├── repository/                   # 数据访问层
│   │   │   │   └── UserRepository.java       # 用户Repository
│   │   │   ├── entity/                       # 实体类
│   │   │   │   └── User.java                 # 用户实体
│   │   │   └── dto/                          # 数据传输对象
│   │   │       ├── UserRegisterRequest.java  # 注册请求DTO
│   │   │       └── ResponseResult.java       # 响应结果DTO
│   │   └── resources/
│   │       ├── application.yml               # YAML配置文件
│   │       ├── application.properties        # Properties配置文件
│   │       └── schema.sql                    # 数据库初始化脚本
│   └── test/                                 # 测试代码
├── pom.xml                                   # Maven配置
└── README.md                                 # 项目说明
```

## 数据验证规则

- 用户名：3-50个字符，不能为空，唯一
- 邮箱：合法邮箱格式，不能为空，唯一
- 手机号：11位手机号码格式，不能为空，唯一
- 密码：6-20个字符，不能为空

## 安全特性

- 密码使用BCrypt加密存储
- 输入数据进行严格验证
- 防止SQL注入和XSS攻击
- 错误信息友好提示

## 许可证

MIT License

## 作者

Wei Shishuo
