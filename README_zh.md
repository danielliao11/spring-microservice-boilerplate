# spring-rest-oauth2-sample

## 目录

- [编译运行](#build)
- [使用](#usage)
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
- [许可证](#license)
- [版本信息](#version)

spring-rest-oauth2-sample，基于以下组件构建：

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring OAuth 2](http://projects.spring.io/spring-security-oauth/)
- [Spring Security](http://projects.spring.io/spring-security/)
- [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)

## <span id="build">编译运行</span>

Linux 及 Unix 使用以下命令。

```
$ cd <spring-rest-oauth2-sample root path>
$ ./gradlew clean build bootRun
```

Windows 下，直接双击 gradlew.bat 运行。

## <span id="usage">使用</span>

### <span id="seed">先运行[Seed](src/test/java/com/saintdan/framework/repo/Seed.java)</span>

### <span id="access_token">获取 access_token</span>

在终端中，通过访问 `oauth/token` 来获取你的 `access_token`，如果你使用了 `ssl` 方式，记得加入 `-k` 参数：

```
$ curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app"
```

或者使用 Chrome 应用 [Advanced REST Client](https://github.com/jarrodek/advanced-rest-client)：

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
playload: password=admin&username=admin&grant_type=password&scope=read
```

### <span id="refresh_token">使用 **refresh_token** 来获取新的 **access_token**</span>

```
curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=<refresh_token_returned>&client_secret=123456&client_id=ios_app"
```

或者使用 Advanced REST Client：

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
playload: grant_type=refresh_token&refresh_token=<refresh_token_returned>
```

### <span id="welcome">访问 welcome 资源</span>

将返回的 **access_token** 放入请求 **Header** 来访问资源：

```
$ curl http://localhost:8080/welcome -H "Authorization: Bearer <access_token_returned>"
```
如果请求成功，将收到以下 JSON 响应：

```
{
	"id":2,
	"content":"Hello, admin!"
}
```

或使用 Advanced REST Client：

```
url: http://localhost:8080/welcome
GET
headers: Authorization: bearer <access_token_returned>
```

### <span id="user">访问用户资源</span>

#### <span id="create">1. 创建用户</span>

```
curl -X POST "http://localhost:8080/resources/users/sign=QWtrbUdQJTJGcmpQcWRGcHpPWWVRUk1BWXNLJTJGWFN5b2Z3RFNOUVNuQ1drNiUyQjQ5dHB0amZzaGpIVER0WWhxOEU2bUpmZkVBbHk1NW9uVDdNSEFjOGpsalhSeVduY3puMzVXQkMwUE1UZ1BYZXdORG9LY0xMV2FKTzZXJTJGY0NhbTh6THolMkYzQ3I2JTJGQiUyQllTS0ZOQ1NVMCUyQndLN3JBM1JtTEVuRWNrTyUyQjBreGNtZVNEUFk5bVFhRUNJNWd2eG5wWUVoUE02WDVMV01Kak55VkdsTjQ5ZUJHUm42a3dYbHQzbWxzTjBwT2ozQWdkTHlHVkZhdTJMaWdjRHljNGQzaFpnMmtvbiUyQkhZTDRMN2VuZE91VWFRaXFIOU1iaWtwdUxjOFhNbHhYOFVUUW1CSHVzTno2SkZzciUyQlFtRkRyT1hUdlN3d2dsSFl3Tlk1Qk55SGRlZlNGMWh0OVJhZyUzRCUzRA==" -H "Authorization: bearer <access_token_returned>" -d "usr=tom&name=tom&pwd=tom"
```

如果请求成功，将收到以下 JSON 响应：

```
{
    "code": "200", 
    "operationStatus": "SUCCESS", 
    "message": "Create user successfully.", 
    "id": 4, 
    "name": "tom", 
    "usr": "tom", 
    "description": null
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/users/sign=QWtrbUdQJTJGcmpQcWRGcHpPWWVRUk1BWXNLJTJGWFN5b2Z3RFNOUVNuQ1drNiUyQjQ5dHB0amZzaGpIVER0WWhxOEU2bUpmZkVBbHk1NW9uVDdNSEFjOGpsalhSeVduY3puMzVXQkMwUE1UZ1BYZXdORG9LY0xMV2FKTzZXJTJGY0NhbTh6THolMkYzQ3I2JTJGQiUyQllTS0ZOQ1NVMCUyQndLN3JBM1JtTEVuRWNrTyUyQjBreGNtZVNEUFk5bVFhRUNJNWd2eG5wWUVoUE02WDVMV01Kak55VkdsTjQ5ZUJHUm42a3dYbHQzbWxzTjBwT2ozQWdkTHlHVkZhdTJMaWdjRHljNGQzaFpnMmtvbiUyQkhZTDRMN2VuZE91VWFRaXFIOU1iaWtwdUxjOFhNbHhYOFVUUW1CSHVzTno2SkZzciUyQlFtRkRyT1hUdlN3d2dsSFl3Tlk1Qk55SGRlZlNGMWh0OVJhZyUzRCUzRA==
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tom&name=tom&pwd=tom
```

#### <span id="all">2. 显示所有用户</span>

```
$ curl "http://localhost:8080/resources/users/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：
```
{
    "code": "200", 
    "operationStatus": "SUCCESS", 
    "message": "Get all users data successfully.", 
    "userVOList": [
        {
            "code": null, 
            "operationStatus": null, 
            "message": null, 
            "id": 1, 
            "name": "root", 
            "usr": "root", 
            "description": "root account"
        }, 
        {
            "code": null, 
            "operationStatus": null, 
            "message": null, 
            "id": 2, 
            "name": "admin", 
            "usr": "admin", 
            "description": "admin account"
        }, 
        {
            "code": null, 
            "operationStatus": null, 
            "message": null, 
            "id": 3, 
            "name": "guest", 
            "usr": "guest", 
            "description": "guest account"
        }, 
        {
            "code": null, 
            "operationStatus": null, 
            "message": null, 
            "id": 4, 
            "name": "tom", 
            "usr": "tom", 
            "description": null
        }
    ]
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/users/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <span id="page">3. 在分页中显示用户</span>

```
$ curl "http://localhost:8080/resources/users/pageNo=0/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：
```
{
    "code": "200", 
    "operationStatus": "SUCCESS", 
    "message": "Get all users data successfully.", 
    "page": {
        "content": [
            {
                "code": null, 
                "operationStatus": null, 
                "message": null, 
                "id": 1, 
                "name": "root", 
                "usr": "root", 
                "description": "root account"
            }, 
            {
                "code": null, 
                "operationStatus": null, 
                "message": null, 
                "id": 2, 
                "name": "admin", 
                "usr": "admin", 
                "description": "admin account"
            }, 
            {
                "code": null, 
                "operationStatus": null, 
                "message": null, 
                "id": 3, 
                "name": "guest", 
                "usr": "guest", 
                "description": "guest account"
            },
            {
            "code": null, 
            "operationStatus": null, 
            "message": null, 
            "id": 4, 
            "name": "tom", 
            "usr": "tom", 
            "description": null
            }
        ], 
        "totalElements": 4, 
        "totalPages": 1, 
        "last": true, 
        "size": 20, 
        "number": 0, 
        "first": true, 
        "sort": null, 
        "numberOfElements": 4
    }
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/users/pageNo=0/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <span id="show_by_id">4. 根据用户 ID 显示用户信息</span>

```
$ curl "http://localhost:8080/resources/users/4/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：

```
{
    "code": "200", 
    "operationStatus": "SUCCESS", 
    "message": "Get user data successfully.", 
    "id": 4, 
    "name": "tom", 
    "usr": "tom", 
    "description": null
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/users/4/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <span id="show_by_usr">5. 根据用户名显示用户信息</span>

```
$ curl "http://localhost:8080/resources/users/usr=admin/sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：

```
{
	code: "200"
	operationStatus: "SUCCESS"
	message: "Get user data successfully."
	name: "admin"
	username: "admin"
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/users/usr=admin/sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

#### <span id="update">6. 根据用户 ID 更新用户数据</span>

```
curl -X POST "http://localhost:8080/resources/users/4/sign=WTdORDVmcGxZcjh6cTVGU3Z0bkIlMkZYTSUyQjNkS0E0N3FHeWxsV0VyaFZ1UHNUUHg4Y2klMkZ0YnRhdVFUcUd2VmJYMXVpVXBBZU45VmJKSG03VVhIM3h2YVUwQ3pjJTJGZyUyQlhDMllrR25ybGVEQVlDbTFySWdWTG9Ca0x0Z2x3WTBRY3BHSjdudVdKSDQ2Y214dG8lMkI3VkNVbHljSTlwd3FkVGx4dk1rd2NtTEFFQnplSDg0RmpsUzBhVTg5JTJCa3AxbGRXNURYeFAyNVpNUnp5RU1xTnl3OWJnQlZTWXFnT3NVMUtPUTJVdmg5WEFmd2FyeWk2elRkeSUyRmpkOHRpRWRHaEUwcHl6Z2pwZzNuWnI2MWcwZW5ndVBHJTJGWXdDc25TdUc3STJBN29ZU1hPYTI4aG9WZWVLQ1F1NzZOT0xUMVk3YU1oUVZvJTJCSWdTVU1kaVdsaGZQaE9kaXB6UnclM0QlM0Q=" -H "Authorization: bearer <access_token_returned>" -d "usr=tom&name=jerry&pwd=tom"
```

如果请求成功，将收到以下 JSON 响应：

```
{
    "code": "200", 
    "operationStatus": "SUCCESS", 
    "message": "Create user successfully.", 
    "id": 4, 
    "name": "tom", 
    "usr": "tom", 
    "description": null
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/users/4/sign=WTdORDVmcGxZcjh6cTVGU3Z0bkIlMkZYTSUyQjNkS0E0N3FHeWxsV0VyaFZ1UHNUUHg4Y2klMkZ0YnRhdVFUcUd2VmJYMXVpVXBBZU45VmJKSG03VVhIM3h2YVUwQ3pjJTJGZyUyQlhDMllrR25ybGVEQVlDbTFySWdWTG9Ca0x0Z2x3WTBRY3BHSjdudVdKSDQ2Y214dG8lMkI3VkNVbHljSTlwd3FkVGx4dk1rd2NtTEFFQnplSDg0RmpsUzBhVTg5JTJCa3AxbGRXNURYeFAyNVpNUnp5RU1xTnl3OWJnQlZTWXFnT3NVMUtPUTJVdmg5WEFmd2FyeWk2elRkeSUyRmpkOHRpRWRHaEUwcHl6Z2pwZzNuWnI2MWcwZW5ndVBHJTJGWXdDc25TdUc3STJBN29ZU1hPYTI4aG9WZWVLQ1F1NzZOT0xUMVk3YU1oUVZvJTJCSWdTVU1kaVdsaGZQaE9kaXB6UnclM0QlM0Q=
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tom&name=jerry&pwd=tom
```

#### <span id="delete">7. 根据用户 ID 删除用户信息</span>

```
curl -X DELETE "http://localhost:8080/resources/users/4/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: bearer <access_token_returned>"
```

如果请求成功，将收到以下 JSON 响应：

```
{
    "code": "200", 
    "operationStatus": "SUCCESS", 
    "message": "Delete user data successfully."
}
```

或使用 Advanced REST Client:

```
url: http://localhost:8080/resources/users/4/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
DELETE
headers: Authorization: bearer <access_token_returned>
```

### <span id="other">其他资源</span>

参照上面的用户资源进行操作.
你可以通过 [SignTest](src/test/java/com/saintdan/framework/tool/SignTest.java) 来生成测试签名串。

## <span id="license">许可证</span>

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2015 saintdan

## <span id="version">Version History</span>

[查看版本信息点我哟～ ;)](VERSION_HISTORY.md)