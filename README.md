# spring-rest-oauth2-sample

[中文版](README_zh.md)

## <a name="index"></a>Index

- [Build and Run](#build)
- [NOTICE](#notice)
- [Usage](#usage)
  - [Import init.sql](#init)
  - [Get **access_token**](#access_token)
  - [Get New **access_token** with **refresh_token**](#refresh_token)
  - [Access to Welcome Resource](#welcome)
  - [Access to User Resource](#user)
    - [1. Create User](#create)
    - [2. Show All Users](#all)
    - [3. Show Users in Page](#page)
    - [4. Show User by id](#show_by_id)
    - [5. Update User by id](#update)
    - [6. Delete User by id](#delete)
  - [Other Resources](#other)
- [Deploy](#deploy)
- [License](#license)
- [Version History](#version)

This is REST service sample be supported by:

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring OAuth 2](http://projects.spring.io/spring-security-oauth/)
- [Spring Security](http://projects.spring.io/spring-security/)
- [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)

And use [specification-arg-resolver](https://github.com/tkaczmarzyk/specification-arg-resolver) for filter.

> NOTE If you need RSA sign check, you can use `validateWithSignCheck` of [ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java)

## <a name="build"></a>Build and Run [[TOP]](#index)

```
$ cd <spring-rest-oauth2-sample root path>
$ ./gradlew clean build bootRun
```

## <a name="notice"></a>NOTICE [[TOP]](#index)

- Validate failed -> Response http status is **422**(Unprocessable Entity)
- Server error -> Response http status is **500**(Internal Server Error)

## <a name="usage"></a>Usage [[TOP]](#index)

### <a name="init"></a>Import the [init.sql](src/main/resources/init.sql) to your database, I suggest you to use [PostgreSQL](https://www.postgresql.org/) [[TOP]](#index)

### <a name="access_token"></a>Get access_token [[TOP]](#index)

Take your token from `oauth/token` in terminal, if you use ssl remember add `-k`:

```
$ curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app"
```

or [Advanced REST client](https://github.com/jarrodek/advanced-rest-client) or [Postman](https://github.com/postmanlabs/) or other REST client in your Chrome with:

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic aW9zX2FwcDoxMjM0NTY= (Encrypt client_id:client_secret by HTTP Basic)
payload: password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app
```

### <a name="refresh_token"></a>Get New **access_token** with **refresh_token** [[TOP]](#index)

```
curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=<refresh_token_returned>&client_secret=123456&client_id=ios_app"
```

or use Advanced REST client / Postman or other REST client:

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
payload: grant_type=refresh_token&refresh_token=<refresh_token_returned>
```

### <a name="welcome"></a>Access to Welcome Resource [[TOP]](#index)

Use the **access_token** returned to make the authorized request to the protected endpoint:

```
$ curl -X GET http://localhost:8080/welcome -H "Authorization: Bearer <access_token_returned>"
```
If the request is successful, your response status is **200**(OK), and your body is:

```
{
  "id": 2,
  "content": "Hello, admin!"
}
```

or use Advanced REST client / Postman or other REST client:

```
url: http://localhost:8080/welcome
GET
headers: Authorization: bearer <access_token_returned>
```

### <a name="user"></a>Access to User Resource [[TOP]](#index)

#### <a name="create"></a>1. Create New User [[TOP]](#index)

```
curl -X POST "http://localhost:8080/resources/v1/users" -H "Authorization: bearer <access_token_returned>" -d "usr=tommy&name=tom&pwd=tom12345"
```

If the request is successful, your response status is **201**(Created), and your body is:

```
{
  "id": 4,
  "name": "tom",
  "usr": "tommy",
  "description": "tom's account"
}
```

or use Advanced REST client / Postman or other REST client:

```
url: http://localhost:8080/resources/v1/users
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tommy&name=tom&pwd=tom12345&description=tom's account
```

#### <a name="all"></a>2. Show All Users [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users" -H "Authorization: bearer <access_token_returned>"
```

If the request is successful, your response status is **200**(OK), and your body is:

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

or use Advanced REST client / Postman or other REST client:

```
url: http://localhost:8080/resources/v1/users
GET
headers: Authorization: bearer <access_token_returned>
```

You can add filter params like:

```
$ curl -X GET "http://localhost:8080/resources/v1/users?name=tom&createdDateAfter=2016-11-01&createdDateBefore=2016-11-30&sortBy=id:desc,name:desc" -H "Authorization: bearer <access_token_returned>"
```

If the request is successful, your response status is **200**(OK), and your body is:

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

#### <a name="page"></a>3. Show Users in Page [[TOP]](#page)

```
$ curl -X GET "http://localhost:8080/resources/v1/users?pageNo=1&pageSize=20&name=tom&sortBy=id:asc,name:desc" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, your response status is **200**(OK), and your body is:

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

or use Advanced REST client / Postman or other REST client:

```
url: http://localhost:8080/resources/v1/users?pageNo=1&pageSize=20&name=tom&sortBy=id:asc,name:desc
GET
headers: Authorization: bearer <access_token_returned>
```

**NOTE:**

Param name | Type | Description
---|---|---
pageNo | int | Must be equal or greater than 1
pageSize | int | Must be equal or greater than 1
sortBy | string | Like paramA:asc,paramB:desc,paramC:asc,...

#### <a name="show_by_id"></a>4. Show User by id [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users/4" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, your response status is **200**(OK), and your body is:

```
{
  "id": 4,
  "name": "tom",
  "usr": "tommy",
  "description": "tom's account"
}
```

or use Advanced REST client / Postman or other REST client:

```
url: http://localhost:8080/resources/v1/users/4
GET
headers: Authorization: bearer <access_token_returned>
```


#### <a id="update"></a>5. Update User by id [[TOP]](#index)

```
curl -X PUT "http://localhost:8080/resources/v1/users/4" -H "Authorization: bearer <access_token_returned>" -d "name=jerry"
```

If the request is successful, your response status is **200**(OK), and your body is:

```
{
  "id": 9,
  "name": "jerry",
  "usr": "tommy",
  "description": "tommy's account"
}
```

or use Advanced REST client / Postman or other REST client:

```
url: http://localhost:8080/resources/v1/users/4
PUT
headers: Authorization: bearer <access_token_returned>
payload: name=jerry
```

#### <a name="delete"></a>6. Delete User by id [[TOP]](#index)

```
curl -X DELETE "http://localhost:8080/resources/v1/users/4" -H "Authorization: bearer <access_token_returned>"
```

If the request is successful, your response status is **204**(No Content)

or use Advanced REST client / Postman or other REST client:

```
url: http://localhost:8080/resources/v1/users/4
DELETE
headers: Authorization: bearer <access_token_returned>
```

### <a name="other"></a>Other resources [[TOP]](#index)

Refer to previous user resource.
And you can generate the sign with [SignTest](src/test/java/com/saintdan/framework/tool/SignTest.java)

### <a name="deploy"></a>Deploy [[TOP]](#index)

1. Build **war** and use tomcat.
2. Build **jar** and run `java -jar foo.jar`
3. Use **Docker**. You can build your docker image by [Dockerfile](Dockerfile). And run it with [docker-compose.yml](docker-compose.yml).

## <a name="license"></a>License [[TOP]](#index)

**[MIT](http://opensource.org/licenses/MIT)**

Copyright (c) since 2015 saintdan

## <a name="version"></a>Version History [[TOP]](#index)

[Version history is here. ;)](VERSION_HISTORY.md)