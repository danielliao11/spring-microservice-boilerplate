# spring-rest-oauth2-sample

[English Version](README.md)

## <a name="index"></a>目录

- [编译运行](#build)
- [注意](#notice)
- [使用](#usage)
  - [导入init.sql](#init)
  - [获取 **access_token**](#access_token)
  - [使用 **refresh_token** 来获取新的 **access_token**](#refresh_token)
  - [访问welcome资源](#welcome)
  - [访问用户资源](#user)
    - [1. 创建用户](#create)
    - [2. 显示所有用户](#all)
    - [3. 分页显示用户](#page)
    - [4. 按照用户ID查看用户信息](#show_by_id)
    - [5. 按照用户ID更新用户信息](#update)
    - [6. 按照用户ID删除用户信息](#delete)
  - [其他资源](#other)
- [部署](#deploy)
- [许可证](#license)
- [版本信息](#version)

spring-rest-oauth2-sample，基于以下组件构建：

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring OAuth 2](http://projects.spring.io/spring-security-oauth/)
- [Spring Security](http://projects.spring.io/spring-security/)
- [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)

并且使用 [specification-arg-resolver](https://github.com/tkaczmarzyk/specification-arg-resolver) 作为过滤器.

> NOTE 如果要使用RSA验签,请使用[ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java)的`validateWithSignCheck`方法

## <a name="build"></a>编译运行 [[TOP]](#index)

Linux 及 Unix 使用以下命令。

```
$ cd <spring-rest-oauth2-sample root path>
$ ./gradlew clean build bootRun
```

Windows 下，直接双击 gradlew.bat 运行。

## <a name="notice"></a>注意 [[TOP]](#index)

- Validate failed -> Response http status is **422**(Unprocessable Entity)
- Server error -> Response http status is **500**(Internal Server Error)

## <a name="usage"></a>使用 [[TOP]](#index)

### <a name="init">导入[init.sql](src/main/resources/init.sql)到您的数据库，建议使用 [PostgreSQL](https://www.postgresql.org/) [[TOP]](#index)

### <a name="access_token"></a>获取 access_token [[TOP]](#index)

在终端中，通过访问 `oauth/token` 来获取你的 `access_token`，如果你使用了 `ssl` 方式，记得加入 `-k` 参数：

```
$ curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app"
```

或者使用 Chrome 应用 [Advanced REST Client](https://github.com/jarrodek/advanced-rest-client) 或者 [Postman](https://github.com/postmanlabs/) 或者其他REST client：

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic aW9zX2FwcDoxMjM0NTY= (Encrypt client_id:client_secret by HTTP Basic)
payload: password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app
```

### <a name="refresh_token"></a>使用 **refresh_token** 来获取新的 **access_token** [[TOP]](#index)

```
curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=<refresh_token_returned>&client_secret=123456&client_id=ios_app"
```

或者使用 Advanced REST Client：

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
payload: grant_type=refresh_token&refresh_token=<refresh_token_returned>
```

### <a name="welcome"></a>访问 welcome 资源 [[TOP]](#index)

将返回的 **access_token** 放入请求 **Header** 来访问资源：

```
$ curl http://localhost:8080/welcome -H "Authorization: Bearer <access_token_returned>"
```
如果请求成功，你的response status为**201**(Created)，body为：：

```
{
  "id": 2,
  "content": "Hello, admin!"
}
```

或使用 Advanced REST Client：

```
url: http://localhost:8080/welcome
GET
headers: Authorization: bearer <access_token_returned>
```

### <a name="user"></a>访问用户资源 [[TOP]](#index)

#### <a name="create"></a>1. 创建用户 [[TOP]](#index)

```
curl -X POST "http://localhost:8080/resources/v1/users" -H "Authorization: bearer <access_token_returned>" -d "usr=tommy&name=tom&pwd=tom12345"
```

如果请求成功，你的response status为**201**(Created)，body为：

```
{
  "id": 4,
  "name": "tom",
  "usr": "tommy",
  "description": "tom's account"
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tommy&name=tom&pwd=tom12345
```

#### <a name="all"></a>2. 显示所有用户 [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users" -H "Authorization: bearer <access_token_returned>"
```

如果请求成功，你的response status为**201**(Created)，body为：：

```
[
  {
    "id": 1,
    "name": "root",
    "usr": "root",
    "description": "root account"
  },
  {
    "id": 2,
    "name": "admin",
    "usr": "admin",
    "description": "admin account"
  },
  {
    "id": 3,
    "name": "guest",
    "usr": "guest",
    "description": "guest account"
  },
  {
    "id": 4,
    "name": "tom",
    "usr": "tommy",
    "description": "tom's account"
  }
]
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users
GET
headers: Authorization: bearer <access_token_returned>
```

你也可以像这样加一些参数过滤:

```
$ curl -X GET "http://localhost:8080/resources/v1/users?name=tom&createdDateAfter=2016-06-01&createdDateBefore=2016-07-30&sortBy=id:desc,name:desc" -H "Authorization: bearer <access_token_returned>"
```

如果请求成功，你的response status为**201**(Created)，body为：：

```
[
  {
    "id": 4,
    "name": "tom",
    "usr": "tommy",
    "description": "tom's account"
  }
]
```

#### <a name="page"></a>3. 在分页中显示用户 [[TOP]](#page)

```
$ curl -X GET "http://localhost:8080/resources/v1/users?pageNo=1&pageSize=20&name=tom&sortBy=id:asc,name:desc" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下类似 JSON 响应：

```
{
  "content": [
    {
      "id": 9,
      "name": "tom",
      "usr": "tommy",
      "description": "tom's account"
    }
  ],
  "totalElements": 1,
  "last": true,
  "totalPages": 1,
  "size": 20,
  "number": 0,
  "sort": [
    {
      "direction": "ASC",
      "property": "id",
      "ignoreCase": false,
      "nullHandling": "NATIVE",
      "ascending": true
    },
    {
      "direction": "DESC",
      "property": "name",
      "ignoreCase": false,
      "nullHandling": "NATIVE",
      "ascending": false
    }
  ],
  "first": true,
  "numberOfElements": 1
}

```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users?pageNo=1&pageSize=20&name=tom&sortBy=id:asc,name:desc
GET
headers: Authorization: bearer <access_token_returned>
```

**NOTE:**

参数名 | 类型 | 描述
---|---|---
pageNo | int | 必须大于等于1
pageSize | int | 必须大于等于1
sortBy | string | 格式类似 paramA:asc,paramB:desc,paramC:asc,...

#### <a name="show_by_id"></a>4. 根据用户 ID 显示用户信息 [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users/4" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：

```
{
  "id": 4,
  "name": "tom",
  "usr": "tommy",
  "description": "tom's account"
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/4
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="update"></a>6. 根据用户 ID 更新用户数据 [[TOP]](#index)

```
curl -X PUT "http://localhost:8080/resources/v1/users/4" -H "Authorization: bearer <access_token_returned>" -d "name=jerry"
```

如果请求成功，将收到以下 JSON 响应：

```
{
  "id": 9,
  "name": "jerry",
  "usr": "tommy",
  "description": "tommy's account"
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/4
PUT
headers: Authorization: bearer <access_token_returned>
payload: name=jerry
```

#### <a name="delete"></a>7. 根据用户 ID 删除用户信息 [[TOP]](#index)

```
curl -X DELETE "http://localhost:8080/resources/v1/users/4" -H "Authorization: bearer <access_token_returned>"
```

如果请求成功，你的response status为**204**(No Content)

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/4
DELETE
headers: Authorization: bearer <access_token_returned>
```

### <a name="other"></a>其他资源 [[TOP]](#index)

参照上面的用户资源进行操作.
你可以通过 [SignTest](src/test/java/com/saintdan/framework/tool/SignTest.java) 来生成测试签名串。

### <a name="deploy"></a>部署 [[TOP]](#index)

1. 传统，打个war包丢tomcat里
2. 高效，打成jar包直接通过`java -jar foo.jar`运行
3. 高效独立干净，使用Docker。[Dockerfile](Dockerfile)已写好，按您的需要做修改即可build Docker镜像。此外，[docker-compose.yml](docker-compose.yml)也已写好，方便您快速一键部署Docker容器。

## <a name="license"></a>许可证 [[TOP]](#index)

**[MIT](http://opensource.org/licenses/MIT)**

Copyright (c) since 2015 saintdan

## <a name="version"></a>Version History [[TOP]](#index)

[查看版本信息点我哟～ ;)](VERSION_HISTORY.md)