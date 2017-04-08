# spring-rest-oauth2-sample

[English Version](README.md)

## <a name="index"></a>目录

- [编译运行](#build)
- [注意](#notice)
- [使用](#usage)
  - [导入init.sql](#init)
  - [获取 **access_token**](#access_token)
  - [使用 **refresh_token** 来获取新的 **access_token**](#refresh_token)
  - [使用 **Swagger** 文档访问资源](#swagger)
- [部署](#deploy)
- [许可证](#license)
- [版本信息](#version)

spring-rest-oauth2-sample，是一个方便java后端人员开发的脚手架。对前端友好，适于前后分离。

**优点：**

 - 采用领域驱动模型结构定义package结构；
 - 微服务架构，更加灵活，降低开发及维护的复杂度；
 - 使用 OAuth2 作为授权验证；
 - 使用 json 方式请求数据；
 - 返回标准的 ResponseEntity，并按 HttpStatus 标准返回状态码；
 - 使用 [Gradle](https://gradle.org/) 作为项目构建工具；
 - 使用 [Swagger](http://swagger.io/) 作为文档工具，方便维护文档，同时可使用Swagger做简易的API测试；

本脚手架基于以下组件构建：

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring OAuth 2](http://projects.spring.io/spring-security-oauth/)
- [Spring Security](http://projects.spring.io/spring-security/)
- [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)

并且使用 [specification-arg-resolver](https://github.com/tkaczmarzyk/specification-arg-resolver) 作为过滤器.

> NOTE 如果要使用RSA验签,请使用 [ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java) 的`validateWithSignCheck`方法，并在需要签名的字段上加上注解 [SignField](src/main/java/com/saintdan/framework/annotation/SignField.java) 。

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
$ curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read"
```

或者使用 Chrome 应用 [Advanced REST Client](https://github.com/jarrodek/advanced-rest-client) 或者 [Postman](https://github.com/postmanlabs/) 或者其他REST client：

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic aW9zX2FwcDoxMjM0NTY= (Encrypt client_id:client_secret by HTTP Basic)
payload: password=admin&username=admin&grant_type=password&scope=read
```

### <a name="refresh_token"></a>使用 **refresh_token** 来获取新的 **access_token** [[TOP]](#index)

```
curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=<refresh_token_returned>"
```

或者使用 Advanced REST Client：

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
payload: grant_type=refresh_token&refresh_token=<refresh_token_returned>
```

### <a name="swagger"></a>使用 Swagger 文档访问资源 [[TOP]](#index)

启动项目，在浏览器（程序员当然用Chrome咯 ;) ）中访问 [http://localhost:8080/swagger-ui.html#/](http://localhost:8080/swagger-ui.html#/)（默认端口8080，您可以修改 [application.properties](src/main/resources/application.properties) 中的 `server.port` 来修改端口。）

出现如下页面

然后根据文档提示即可测试。

### <a name="deploy"></a>部署 [[TOP]](#index)

1. 传统，打个war包丢tomcat里
2. 高效，打成jar包直接通过`java -jar foo.jar`运行
3. 高效独立干净，使用Docker。[Dockerfile](Dockerfile)已写好，按您的需要做修改即可build Docker镜像。此外，[docker-compose.yml](docker-compose.yml)也已写好，方便您快速一键部署Docker容器。

## <a name="license"></a>许可证 [[TOP]](#index)

**[MIT](http://opensource.org/licenses/MIT)**

Copyright (c) since 2015 saintdan

## <a name="version"></a>Version History [[TOP]](#index)

[查看版本信息点我哟～ ;)](VERSION_HISTORY.md)