# spring-rest-oauth2-sample

[中文版](README_zh.md)

## <a name="index"></a>Index

- [Build and Run](#build)
- [Usage](#usage)
  - [Use the seed](#seed)
  - [Get **access_token**](#access_token)
  - [Get new **access_token** with **refresh_token**](#refresh_token)
  - [Access to welcome resource](#welcome)
  - [Access to user resource](#user)
    - [1. Create User](#create)
    - [2. Show all users](#all)
    - [3. Show users in page](#page)
    - [4. Show user by user's ID](#show_by_id)
    - [5. Show user by user's usr](#show_by_usr)
    - [6. Update user by user's ID](#update)
    - [7. Delete user by user's ID](#delete)
  - [Other resources](#other)
- [License](#license)
- [Version History](#version)

This is REST service sample be supported by:

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring OAuth 2](http://projects.spring.io/spring-security-oauth/)
- [Spring Security](http://projects.spring.io/spring-security/)
- [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)

## <a name="build"></a>Build and Run [[TOP]](#index)

```
$ cd <spring-rest-oauth2-sample root path>
$ ./gradlew clean build bootRun
```

## <a name="usage"></a>Usage [[TOP]](#index)

### <a name="seed"></a>Use the [Seed](src/test/java/com/saintdan/framework/repo/Seed.java) [[TOP]](#index)

### <a name="access_token"></a>Get access_token [[TOP]](#index)

Take your token from `oauth/token` in terminal, if you use ssl remember add `-k`:

```
$ curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app"
```

or [Advanced REST Client](https://github.com/jarrodek/advanced-rest-client) in your Chrome with:

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
payload: password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app
```

### <a name="refresh_token"></a>Get new **access_token** with **refresh_token** [[TOP]](#index)

```
curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=<refresh_token_returned>&client_secret=123456&client_id=ios_app"
```

or use Advanced REST Client:

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
payload: grant_type=refresh_token&refresh_token=<refresh_token_returned>
```

### <a name="welcome"></a>Access to welcome resource [[TOP]](#index)

Use the **access_token** returned to make the authorized request to the protected endpoint:

```
$ curl http://localhost:8080/welcome -H "Authorization: Bearer <access_token_returned>"
```
If the request is successful, you will see the following JSON response:

```
{
	"id":2,
	"content":"Hello, admin!"
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/welcome
GET
headers: Authorization: bearer <access_token_returned>
```

### <a name="user"></a>Access to user resource [[TOP]](#index)

#### <a name="create"></a>1. Create new user [[TOP]](#index)

```
curl -X POST "http://localhost:8080/resources/users/sign=QWtrbUdQJTJGcmpQcWRGcHpPWWVRUk1BWXNLJTJGWFN5b2Z3RFNOUVNuQ1drNiUyQjQ5dHB0amZzaGpIVER0WWhxOEU2bUpmZkVBbHk1NW9uVDdNSEFjOGpsalhSeVduY3puMzVXQkMwUE1UZ1BYZXdORG9LY0xMV2FKTzZXJTJGY0NhbTh6THolMkYzQ3I2JTJGQiUyQllTS0ZOQ1NVMCUyQndLN3JBM1JtTEVuRWNrTyUyQjBreGNtZVNEUFk5bVFhRUNJNWd2eG5wWUVoUE02WDVMV01Kak55VkdsTjQ5ZUJHUm42a3dYbHQzbWxzTjBwT2ozQWdkTHlHVkZhdTJMaWdjRHljNGQzaFpnMmtvbiUyQkhZTDRMN2VuZE91VWFRaXFIOU1iaWtwdUxjOFhNbHhYOFVUUW1CSHVzTno2SkZzciUyQlFtRkRyT1hUdlN3d2dsSFl3Tlk1Qk55SGRlZlNGMWh0OVJhZyUzRCUzRA==" -H "Authorization: bearer <access_token_returned>" -d "usr=tom&name=tom&pwd=tom"
```

If the request is successful, you will see the following JSON response:

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

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users/sign=QWtrbUdQJTJGcmpQcWRGcHpPWWVRUk1BWXNLJTJGWFN5b2Z3RFNOUVNuQ1drNiUyQjQ5dHB0amZzaGpIVER0WWhxOEU2bUpmZkVBbHk1NW9uVDdNSEFjOGpsalhSeVduY3puMzVXQkMwUE1UZ1BYZXdORG9LY0xMV2FKTzZXJTJGY0NhbTh6THolMkYzQ3I2JTJGQiUyQllTS0ZOQ1NVMCUyQndLN3JBM1JtTEVuRWNrTyUyQjBreGNtZVNEUFk5bVFhRUNJNWd2eG5wWUVoUE02WDVMV01Kak55VkdsTjQ5ZUJHUm42a3dYbHQzbWxzTjBwT2ozQWdkTHlHVkZhdTJMaWdjRHljNGQzaFpnMmtvbiUyQkhZTDRMN2VuZE91VWFRaXFIOU1iaWtwdUxjOFhNbHhYOFVUUW1CSHVzTno2SkZzciUyQlFtRkRyT1hUdlN3d2dsSFl3Tlk1Qk55SGRlZlNGMWh0OVJhZyUzRCUzRA==
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tom&name=tom&pwd=tom
```

#### 2. <a name="all"></a>Show all users [[TOP]](#index)

```
$ curl "http://localhost:8080/resources/users/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response:

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

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="page"></a>3. Show users in page [[TOP]](#index)

```
$ curl "http://localhost:8080/resources/users/pageNo=0/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response:

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

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users/pageNo=0/sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="show_by_id"></a>4. Show user by user's ID [[TOP]](#index)

```
$ curl "http://localhost:8080/resources/users/4/sign=ZUNjN3VUMVp4RVV6TnM4WDJocUhRJTJCNmxZZWNsdEZaZ3NXdWJCd1E1RkpTTWVmWUhsazRPRXZuV2hZZnM1MjU2SkRQJTJCUUFQR2hob0VsRmZieiUyRkVZNXl6dEhqa05relUlMkZUS1duc1hGTmp0NCUyRkU1SGxYcUtnQ21VUFp6OG82NVQwMVd6MXRrazVCQW5iY3FKb0xBNVVlY0l4VWhVTkM1dXdFRzUxMUVIeUwxWUZ3TGY4JTJGJTJGVlZ4Q2lqTERVZ1F2WDJ4OW5JVFJuZUVjVVFHTzFMdFhEb25hVGU3OWpSUjJXV0Q0SGZ4QXk1ZDN6MzFlRmgzMUlnRjhwbW9FTU0ya0h0d0VEJTJGUnBvdFZmMHRwZ0R2NVE0aGlaMGhsdlFoNDJrTHElMkJBekZtU3pFNkRRSmU4dHJWUWJ6dHhmJTJGSU04YlJ2TXdxalAxdDJ4UEF6dHB5aTZhUzh5TlElM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response:

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

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users/4/sign=ZUNjN3VUMVp4RVV6TnM4WDJocUhRJTJCNmxZZWNsdEZaZ3NXdWJCd1E1RkpTTWVmWUhsazRPRXZuV2hZZnM1MjU2SkRQJTJCUUFQR2hob0VsRmZieiUyRkVZNXl6dEhqa05relUlMkZUS1duc1hGTmp0NCUyRkU1SGxYcUtnQ21VUFp6OG82NVQwMVd6MXRrazVCQW5iY3FKb0xBNVVlY0l4VWhVTkM1dXdFRzUxMUVIeUwxWUZ3TGY4JTJGJTJGVlZ4Q2lqTERVZ1F2WDJ4OW5JVFJuZUVjVVFHTzFMdFhEb25hVGU3OWpSUjJXV0Q0SGZ4QXk1ZDN6MzFlRmgzMUlnRjhwbW9FTU0ya0h0d0VEJTJGUnBvdFZmMHRwZ0R2NVE0aGlaMGhsdlFoNDJrTHElMkJBekZtU3pFNkRRSmU4dHJWUWJ6dHhmJTJGSU04YlJ2TXdxalAxdDJ4UEF6dHB5aTZhUzh5TlElM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="show_by_usr"></a>5. Show user by user's usr [[TOP]](#index)

```
$ curl "http://localhost:8080/resources/users/usr=admin/sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response:

```
{
	code: "200"
	operationStatus: "SUCCESS"
	message: "Get user data successfully."
	name: "admin"
	username: "admin"
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users/usr=admin/sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a id="update"></a>6. Update user by user's ID [[TOP]](#index)

```
curl -X POST "http://localhost:8080/resources/users/4/sign=SGdCVm5ZQ253U0RqcTlaY1h1VkRITFN5emtoVEVnQlNvZzRHTDhaT1dWT0g4UWlyUG5MT1lyU21tdGZ3T1dGWiUyRldRWHdGSmlhbE13T3dmdlVseFBkb3NRZmVxZUw0eXhmYjYzUFA5d0JSbzdnVk45OHh3MlNKUVoyR3ZieW1zcUp2RnBybWZtJTJCdzcyV2JIVmRUbE13SiUyRm9CZ3k4JTJGaWIlMkZFejJVRGxTVjJtVHN2R2dzYktWemxlaVRteEhZRGxhZEdVdGtpMHZzSVNtMSUyRjJSN2dIUGV3UG9RRUJIc2N5dzAwdXJHVURVMUF2Zld1eVJBY0MweTVZTHhLWEt6WUY4TEttTU82UE1KbU82dTJydFQ4dkpQOVJkWFdDJTJCTjhRMGpuTkxiMVR6T2JjJTJCOHBLSEJjNnE5TGdjZHRnTGNXdUZNT3c0S0JFbUZVbjN2UmZ0N01iTWRLZyUzRCUzRA==" -H "Authorization: bearer <access_token_returned>" -d "usr=tom&name=jerry&pwd=tom"
```

If the request is successful, you will see the following JSON response:

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

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users/4/sign=SGdCVm5ZQ253U0RqcTlaY1h1VkRITFN5emtoVEVnQlNvZzRHTDhaT1dWT0g4UWlyUG5MT1lyU21tdGZ3T1dGWiUyRldRWHdGSmlhbE13T3dmdlVseFBkb3NRZmVxZUw0eXhmYjYzUFA5d0JSbzdnVk45OHh3MlNKUVoyR3ZieW1zcUp2RnBybWZtJTJCdzcyV2JIVmRUbE13SiUyRm9CZ3k4JTJGaWIlMkZFejJVRGxTVjJtVHN2R2dzYktWemxlaVRteEhZRGxhZEdVdGtpMHZzSVNtMSUyRjJSN2dIUGV3UG9RRUJIc2N5dzAwdXJHVURVMUF2Zld1eVJBY0MweTVZTHhLWEt6WUY4TEttTU82UE1KbU82dTJydFQ4dkpQOVJkWFdDJTJCTjhRMGpuTkxiMVR6T2JjJTJCOHBLSEJjNnE5TGdjZHRnTGNXdUZNT3c0S0JFbUZVbjN2UmZ0N01iTWRLZyUzRCUzRA==
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tom&name=jerry&pwd=tom
```

#### <a name="delete"></a>7. Delete user by user's ID [[TOP]](#index)

```
curl -X DELETE "http://localhost:8080/resources/users/4/sign=ZUNjN3VUMVp4RVV6TnM4WDJocUhRJTJCNmxZZWNsdEZaZ3NXdWJCd1E1RkpTTWVmWUhsazRPRXZuV2hZZnM1MjU2SkRQJTJCUUFQR2hob0VsRmZieiUyRkVZNXl6dEhqa05relUlMkZUS1duc1hGTmp0NCUyRkU1SGxYcUtnQ21VUFp6OG82NVQwMVd6MXRrazVCQW5iY3FKb0xBNVVlY0l4VWhVTkM1dXdFRzUxMUVIeUwxWUZ3TGY4JTJGJTJGVlZ4Q2lqTERVZ1F2WDJ4OW5JVFJuZUVjVVFHTzFMdFhEb25hVGU3OWpSUjJXV0Q0SGZ4QXk1ZDN6MzFlRmgzMUlnRjhwbW9FTU0ya0h0d0VEJTJGUnBvdFZmMHRwZ0R2NVE0aGlaMGhsdlFoNDJrTHElMkJBekZtU3pFNkRRSmU4dHJWUWJ6dHhmJTJGSU04YlJ2TXdxalAxdDJ4UEF6dHB5aTZhUzh5TlElM0QlM0Q=" -H "Authorization: bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response:

```
{
    "code": "200", 
    "operationStatus": "SUCCESS", 
    "message": "Delete user data successfully."
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users/4/sign=ZUNjN3VUMVp4RVV6TnM4WDJocUhRJTJCNmxZZWNsdEZaZ3NXdWJCd1E1RkpTTWVmWUhsazRPRXZuV2hZZnM1MjU2SkRQJTJCUUFQR2hob0VsRmZieiUyRkVZNXl6dEhqa05relUlMkZUS1duc1hGTmp0NCUyRkU1SGxYcUtnQ21VUFp6OG82NVQwMVd6MXRrazVCQW5iY3FKb0xBNVVlY0l4VWhVTkM1dXdFRzUxMUVIeUwxWUZ3TGY4JTJGJTJGVlZ4Q2lqTERVZ1F2WDJ4OW5JVFJuZUVjVVFHTzFMdFhEb25hVGU3OWpSUjJXV0Q0SGZ4QXk1ZDN6MzFlRmgzMUlnRjhwbW9FTU0ya0h0d0VEJTJGUnBvdFZmMHRwZ0R2NVE0aGlaMGhsdlFoNDJrTHElMkJBekZtU3pFNkRRSmU4dHJWUWJ6dHhmJTJGSU04YlJ2TXdxalAxdDJ4UEF6dHB5aTZhUzh5TlElM0QlM0Q=
DELETE
headers: Authorization: bearer <access_token_returned>
```

### <a name="other"></a>Other resources [[TOP]](#index)

Refer to previous user resource.
And you can generate the sign with [SignTest](src/test/java/com/saintdan/framework/tool/SignTest.java)

## <a name="license"></a>License [[TOP]](#index)

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2015 saintdan

## <a name="version"></a>Version History [[TOP]](#index)

[Version history is here. ;)](VERSION_HISTORY.md)