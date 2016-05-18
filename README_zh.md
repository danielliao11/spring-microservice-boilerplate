# spring-rest-oauth2-sample

[English Version](README.md)

## <a name="index"></a>目录

- [编译运行](#build)
- [使用](#usage)
  - [数据格式](#data_format)
  - [获取 **access_token**](#access_token)
  - [使用 **refresh_token** 来获取新的 **access_token**](#refresh_token)
  - [访问welcome资源](#welcome)
  - [访问用户资源](#user)
    - [1. 创建用户](#create)
    - [2. 显示所有用户](#all)
    - [3. 分页显示用户](#page)
    - [4. 按照用户ID查看用户信息](#show_by_id)
    - [5. 按照用户名查看用户信息](#show_by_usr)
    - [6. 按照用户ID更新用户信息](#update)
    - [7. 按照用户ID删除用户信息](#delete)
  - [其他资源](#other)
- [部署](#deploy)
- [许可证](#license)
- [版本信息](#version)

spring-rest-oauth2-sample，基于以下组件构建：

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring OAuth 2](http://projects.spring.io/spring-security-oauth/)
- [Spring Security](http://projects.spring.io/spring-security/)
- [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)

## <a name="build"></a>编译运行 [[TOP]](#index)

Linux 及 Unix 使用以下命令。

```
$ cd <spring-rest-oauth2-sample root path>
$ ./gradlew clean build bootRun
```

Windows 下，直接双击 gradlew.bat 运行。

## <a name="usage"></a>使用 [[TOP]](#index)

### <a name="data_format"></a>数据格式 [[TOP]](#index)

返回数据格式为：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {}
}
```

### <a name="seed">先运行[Seed](src/test/java/com/saintdan/framework/repo/Seed.java) [[TOP]](#index)

### <a name="access_token"></a>获取 access_token [[TOP]](#index)

在终端中，通过访问 `oauth/token` 来获取你的 `access_token`，如果你使用了 `ssl` 方式，记得加入 `-k` 参数：

```
$ curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app"
```

或者使用 Chrome 应用 [Advanced REST Client](https://github.com/jarrodek/advanced-rest-client)：

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
如果请求成功，将收到类似以下 JSON 响应：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 4,
    "content": "Hello, admin!"
  }
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
curl -X POST "http://localhost:8080/resources/v1/users" -H "Authorization: bearer <access_token_returned>" -d "usr=tommy&name=tom&pwd=tom12345&sign=WElnaHE3d2hLM3B6MTBqcTZrM2FOOTdUV0N5cldKcDJuTWMxRW1hck55Qk1FbzRxZm9xMVJzdk9ITTFFNjhQZGxqc0k1ZTdDcmJaemwyN2tFckFHZ3ZKeW1wQVpvRFAlMkZOdm9hWDFkU2s2TzNVVE5YME03NzI0ODklMkY0eXpCUk1kVDFKbHczWEE0RDZCeWo5YmdSbDkwT3FGcE9rSlglMkZ2TVJJVCUyQmNzRXQzdDZ2ZkExUmhaS1QwaVJTTHI1Snhwd3U4dThsbDhyNm1BOXAlMkZmcUprUTVnUzQ1WHV3UTRWJTJCS2dKRlVMaXBTV2NiJTJGOE9INTlhRWd0eU1ZSTJjS1cybk1samhZSVN5YmUyZlluSmw4V0RLMVhtaWo5R3NKaVJUMHR6JTJGM25rWldocHZNVVBiSUJPTTc5WUhnJTJGNXlOUnFIS0VudGslMkZBMXAwMkViZ2haSW5FOHF5VVElM0QlM0Q="
```

如果请求成功，将收到类似以下 JSON 响应：

```
{
  "code": "200"
  "operationStatus": "SUCCESS"
  "message": "Successfully"
  "data": {
    "id": 29
    "name": "tom"
    "usr": "tommy"
    "description": null
  }
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tommy&name=tom&pwd=tom12345&sign=WElnaHE3d2hLM3B6MTBqcTZrM2FOOTdUV0N5cldKcDJuTWMxRW1hck55Qk1FbzRxZm9xMVJzdk9ITTFFNjhQZGxqc0k1ZTdDcmJaemwyN2tFckFHZ3ZKeW1wQVpvRFAlMkZOdm9hWDFkU2s2TzNVVE5YME03NzI0ODklMkY0eXpCUk1kVDFKbHczWEE0RDZCeWo5YmdSbDkwT3FGcE9rSlglMkZ2TVJJVCUyQmNzRXQzdDZ2ZkExUmhaS1QwaVJTTHI1Snhwd3U4dThsbDhyNm1BOXAlMkZmcUprUTVnUzQ1WHV3UTRWJTJCS2dKRlVMaXBTV2NiJTJGOE9INTlhRWd0eU1ZSTJjS1cybk1samhZSVN5YmUyZlluSmw4V0RLMVhtaWo5R3NKaVJUMHR6JTJGM25rWldocHZNVVBiSUJPTTc5WUhnJTJGNXlOUnFIS0VudGslMkZBMXAwMkViZ2haSW5FOHF5VVElM0QlM0Q=
```

#### <a name="all"></a>2. 显示所有用户 [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users/index?sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: bearer <access_token_returned>"
```

如果请求成功，将收到类似以下 JSON 响应：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "objects": [
      {
        "id": 1,
        "name": "root",
        "usr": "root",
        "description": "root account"
      },
      {
        "id": 5,
        "name": "guest",
        "usr": "guest",
        "description": "guest account"
      },
      {
        "id": 29,
        "name": "tom",
        "usr": "tommy",
        "description": null
      },
      {
        "id": 4,
        "name": "admin",
        "usr": "admin",
        "description": "admin account"
      }
    ]
  }
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/index?sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="page"></a>3. 在分页中显示用户 [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users?pageNo=0&sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下类似 JSON 响应：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "page": {
      "content": [
        {
          "id": 1,
          "name": "root",
          "usr": "root",
          "description": "root account"
        },
        {
          "id": 4,
          "name": "admin",
          "usr": "admin",
          "description": "admin account"
        },
        {
          "id": 5,
          "name": "guest",
          "usr": "guest",
          "description": "guest account"
        },
        {
          "id": 29,
          "name": "tom",
          "usr": "tommy",
          "description": null
        }
      ],
      "totalElements": 4,
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
        }
      ],
      "first": true,
      "numberOfElements": 4
    }
  }
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/index?sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="show_by_id"></a>4. 根据用户 ID 显示用户信息 [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users/29?sign=RFpwRXlRJTJCRDI5Q1FHYW95eXk3YVN0b0s2M25kT21ZMjFmWm9PNFAyMzYlMkJ4NlJtVHdTTDREWWY2Wmp0dzIzMWtoa3ZPRkFMbThFYmlFSmszSEFOMW5TaUclMkY2NXlwJTJCMnJ1UTRYZUFJcW1oQm5ZcUVGN0NlbyUyRjFmbmlLalpSaWVLYUlaOHZ2NFJLRWdjaHN3T1FhcE8xOE1wSnBuRTk4JTJGQWJGenBROXNMTjdzaGN4bTFtUzhzRFNLTFNLckZpb1VMSDIxVXAzZmRmWThmUmZhcTRjJTJGcjVUSTcwT0ZFeVVGbnJURUM1SGRGM0hVU2E5V2xPcG9zRiUyRmE3dVJXZEZNWEF6VGV4RlQxYW5mNHYwb245M3hmM3k0WmhqT0lNVlp6eW1kJTJGTUkzZW8yNEFuYkE3VzdLUVFEaHM4UGRUZHZlJTJCaTFLMFJwcmolMkZyS0NSRTElMkJ2ZVhOJTJGN2clM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 4,
    "name": "admin",
    "usr": "admin",
    "description": "admin account"
  }
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/29?sign=RFpwRXlRJTJCRDI5Q1FHYW95eXk3YVN0b0s2M25kT21ZMjFmWm9PNFAyMzYlMkJ4NlJtVHdTTDREWWY2Wmp0dzIzMWtoa3ZPRkFMbThFYmlFSmszSEFOMW5TaUclMkY2NXlwJTJCMnJ1UTRYZUFJcW1oQm5ZcUVGN0NlbyUyRjFmbmlLalpSaWVLYUlaOHZ2NFJLRWdjaHN3T1FhcE8xOE1wSnBuRTk4JTJGQWJGenBROXNMTjdzaGN4bTFtUzhzRFNLTFNLckZpb1VMSDIxVXAzZmRmWThmUmZhcTRjJTJGcjVUSTcwT0ZFeVVGbnJURUM1SGRGM0hVU2E5V2xPcG9zRiUyRmE3dVJXZEZNWEF6VGV4RlQxYW5mNHYwb245M3hmM3k0WmhqT0lNVlp6eW1kJTJGTUkzZW8yNEFuYkE3VzdLUVFEaHM4UGRUZHZlJTJCaTFLMFJwcmolMkZyS0NSRTElMkJ2ZVhOJTJGN2clM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="show_by_usr"></a>5. 根据用户名显示用户信息 [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users/usr/admin?sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 4,
    "name": "admin",
    "usr": "admin",
    "description": "admin account"
  }
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/usr/admin?sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="update"></a>6. 根据用户 ID 更新用户数据 [[TOP]](#index)

```
curl -X POST "http://localhost:8080/resources/v1/users/29" -H "Authorization: bearer <access_token_returned>" -d "usr=tommy&name=jerry&pwd=tom54321&sign=UmslMkZVaFI2Mzc4N3VGd0hzblRXaE9QSE9aNHVZMXdLWmxwcDk0alRndjg2TFFaRkx1NlROUSUyQlN2UFlWWXdIcU50T25LZnk5WlZpUjgzblROYnNBTEJSZ2FHQ29zY29KZm1iOEVsTTFFeUhrbmdRSWVQdk1PTUI5N0RkY1pvd2ZVSHolMkJtalJPWnhDeW9TYkhtJTJGanFPNk9UYWFNanUlMkI3aFRHSm5HaXd2SXV2V0RYbjg4Vnc5ZjZCSUlrVW1UTFJBTXhOdEh6ZlZXWTRNS2ZnV3pzUGZIbllmdWlIM3U3Qmw1cEoxdnJpdVI1TSUyQnhubEpVOXZJOCUyQlpFTDNQVGlLaEllcFRrNjFSMVRCYktFeEhQaWJDUlNmZHVqZkpqMTclMkJYM2kxWTk1emplSmhGSkFnaUVDcmtDUWYlMkZzMkRLUU1vak94YjJ4emdYbXJzSEpSUlpmWkQwQWJ3JTNEJTNE"
```

如果请求成功，将收到以下 JSON 响应：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 29,
    "name": "jerry",
    "usr": "tommy",
    "description": null
  }
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/29
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tommy&name=jerry&pwd=tom54321&sign=UmslMkZVaFI2Mzc4N3VGd0hzblRXaE9QSE9aNHVZMXdLWmxwcDk0alRndjg2TFFaRkx1NlROUSUyQlN2UFlWWXdIcU50T25LZnk5WlZpUjgzblROYnNBTEJSZ2FHQ29zY29KZm1iOEVsTTFFeUhrbmdRSWVQdk1PTUI5N0RkY1pvd2ZVSHolMkJtalJPWnhDeW9TYkhtJTJGanFPNk9UYWFNanUlMkI3aFRHSm5HaXd2SXV2V0RYbjg4Vnc5ZjZCSUlrVW1UTFJBTXhOdEh6ZlZXWTRNS2ZnV3pzUGZIbllmdWlIM3U3Qmw1cEoxdnJpdVI1TSUyQnhubEpVOXZJOCUyQlpFTDNQVGlLaEllcFRrNjFSMVRCYktFeEhQaWJDUlNmZHVqZkpqMTclMkJYM2kxWTk1emplSmhGSkFnaUVDcmtDUWYlMkZzMkRLUU1vak94YjJ4emdYbXJzSEpSUlpmWkQwQWJ3JTNEJTNE
```

#### <a name="delete"></a>7. 根据用户 ID 删除用户信息 [[TOP]](#index)

```
curl -X DELETE "http://localhost:8080/resources/v1/users/29?sign=RFpwRXlRJTJCRDI5Q1FHYW95eXk3YVN0b0s2M25kT21ZMjFmWm9PNFAyMzYlMkJ4NlJtVHdTTDREWWY2Wmp0dzIzMWtoa3ZPRkFMbThFYmlFSmszSEFOMW5TaUclMkY2NXlwJTJCMnJ1UTRYZUFJcW1oQm5ZcUVGN0NlbyUyRjFmbmlLalpSaWVLYUlaOHZ2NFJLRWdjaHN3T1FhcE8xOE1wSnBuRTk4JTJGQWJGenBROXNMTjdzaGN4bTFtUzhzRFNLTFNLckZpb1VMSDIxVXAzZmRmWThmUmZhcTRjJTJGcjVUSTcwT0ZFeVVGbnJURUM1SGRGM0hVU2E5V2xPcG9zRiUyRmE3dVJXZEZNWEF6VGV4RlQxYW5mNHYwb245M3hmM3k0WmhqT0lNVlp6eW1kJTJGTUkzZW8yNEFuYkE3VzdLUVFEaHM4UGRUZHZlJTJCaTFLMFJwcmolMkZyS0NSRTElMkJ2ZVhOJTJGN2clM0QlM0Q=" -H "Authorization: bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Delete user successfully.",
  "data": null
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/29?sign=RFpwRXlRJTJCRDI5Q1FHYW95eXk3YVN0b0s2M25kT21ZMjFmWm9PNFAyMzYlMkJ4NlJtVHdTTDREWWY2Wmp0dzIzMWtoa3ZPRkFMbThFYmlFSmszSEFOMW5TaUclMkY2NXlwJTJCMnJ1UTRYZUFJcW1oQm5ZcUVGN0NlbyUyRjFmbmlLalpSaWVLYUlaOHZ2NFJLRWdjaHN3T1FhcE8xOE1wSnBuRTk4JTJGQWJGenBROXNMTjdzaGN4bTFtUzhzRFNLTFNLckZpb1VMSDIxVXAzZmRmWThmUmZhcTRjJTJGcjVUSTcwT0ZFeVVGbnJURUM1SGRGM0hVU2E5V2xPcG9zRiUyRmE3dVJXZEZNWEF6VGV4RlQxYW5mNHYwb245M3hmM3k0WmhqT0lNVlp6eW1kJTJGTUkzZW8yNEFuYkE3VzdLUVFEaHM4UGRUZHZlJTJCaTFLMFJwcmolMkZyS0NSRTElMkJ2ZVhOJTJGN2clM0QlM0Q=
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

Copyright (c) 2016 saintdan

## <a name="version"></a>Version History [[TOP]](#index)

[查看版本信息点我哟～ ;)](VERSION_HISTORY.md)