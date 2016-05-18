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
headers: Authorization: Basic aW9zX2FwcDoxMjM0NTY= (Encrypt client_id:client_secret by HTTP Basic)
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
$ curl -X GET http://localhost:8080/welcome -H "Authorization: Bearer <access_token_returned>"
```
If the request is successful, you will see the following JSON response:

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

or use Advanced REST Client:

```
url: http://localhost:8080/welcome
GET
headers: Authorization: bearer <access_token_returned>
```

### <a name="user"></a>Access to user resource [[TOP]](#index)

#### <a name="create"></a>1. Create new user [[TOP]](#index)

```
curl -X POST "http://localhost:8080/resources/users" -H "Authorization: bearer <access_token_returned>" -d "usr=tommy&name=tom&pwd=tom12345&sign=WElnaHE3d2hLM3B6MTBqcTZrM2FOOTdUV0N5cldKcDJuTWMxRW1hck55Qk1FbzRxZm9xMVJzdk9ITTFFNjhQZGxqc0k1ZTdDcmJaemwyN2tFckFHZ3ZKeW1wQVpvRFAlMkZOdm9hWDFkU2s2TzNVVE5YME03NzI0ODklMkY0eXpCUk1kVDFKbHczWEE0RDZCeWo5YmdSbDkwT3FGcE9rSlglMkZ2TVJJVCUyQmNzRXQzdDZ2ZkExUmhaS1QwaVJTTHI1Snhwd3U4dThsbDhyNm1BOXAlMkZmcUprUTVnUzQ1WHV3UTRWJTJCS2dKRlVMaXBTV2NiJTJGOE9INTlhRWd0eU1ZSTJjS1cybk1samhZSVN5YmUyZlluSmw4V0RLMVhtaWo5R3NKaVJUMHR6JTJGM25rWldocHZNVVBiSUJPTTc5WUhnJTJGNXlOUnFIS0VudGslMkZBMXAwMkViZ2haSW5FOHF5VVElM0QlM0Q="
```

If the request is successful, you will see the following JSON response like this:

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

or use Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tommy&name=tom&pwd=tom12345&sign=WElnaHE3d2hLM3B6MTBqcTZrM2FOOTdUV0N5cldKcDJuTWMxRW1hck55Qk1FbzRxZm9xMVJzdk9ITTFFNjhQZGxqc0k1ZTdDcmJaemwyN2tFckFHZ3ZKeW1wQVpvRFAlMkZOdm9hWDFkU2s2TzNVVE5YME03NzI0ODklMkY0eXpCUk1kVDFKbHczWEE0RDZCeWo5YmdSbDkwT3FGcE9rSlglMkZ2TVJJVCUyQmNzRXQzdDZ2ZkExUmhaS1QwaVJTTHI1Snhwd3U4dThsbDhyNm1BOXAlMkZmcUprUTVnUzQ1WHV3UTRWJTJCS2dKRlVMaXBTV2NiJTJGOE9INTlhRWd0eU1ZSTJjS1cybk1samhZSVN5YmUyZlluSmw4V0RLMVhtaWo5R3NKaVJUMHR6JTJGM25rWldocHZNVVBiSUJPTTc5WUhnJTJGNXlOUnFIS0VudGslMkZBMXAwMkViZ2haSW5FOHF5VVElM0QlM0Q=
```

#### 2. <a name="all"></a>Show all users [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users/index?sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response:

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

or use Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/index?sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="page"></a>3. Show users in page [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users?pageNo=0&sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response:

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

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users?pageNo=0&sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="show_by_id"></a>4. Show user by user's ID [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users/29?sign=RFpwRXlRJTJCRDI5Q1FHYW95eXk3YVN0b0s2M25kT21ZMjFmWm9PNFAyMzYlMkJ4NlJtVHdTTDREWWY2Wmp0dzIzMWtoa3ZPRkFMbThFYmlFSmszSEFOMW5TaUclMkY2NXlwJTJCMnJ1UTRYZUFJcW1oQm5ZcUVGN0NlbyUyRjFmbmlLalpSaWVLYUlaOHZ2NFJLRWdjaHN3T1FhcE8xOE1wSnBuRTk4JTJGQWJGenBROXNMTjdzaGN4bTFtUzhzRFNLTFNLckZpb1VMSDIxVXAzZmRmWThmUmZhcTRjJTJGcjVUSTcwT0ZFeVVGbnJURUM1SGRGM0hVU2E5V2xPcG9zRiUyRmE3dVJXZEZNWEF6VGV4RlQxYW5mNHYwb245M3hmM3k0WmhqT0lNVlp6eW1kJTJGTUkzZW8yNEFuYkE3VzdLUVFEaHM4UGRUZHZlJTJCaTFLMFJwcmolMkZyS0NSRTElMkJ2ZVhOJTJGN2clM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response:

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 29,
    "name": "tom",
    "usr": "tommy",
    "description": null
  }
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/29?sign=RFpwRXlRJTJCRDI5Q1FHYW95eXk3YVN0b0s2M25kT21ZMjFmWm9PNFAyMzYlMkJ4NlJtVHdTTDREWWY2Wmp0dzIzMWtoa3ZPRkFMbThFYmlFSmszSEFOMW5TaUclMkY2NXlwJTJCMnJ1UTRYZUFJcW1oQm5ZcUVGN0NlbyUyRjFmbmlLalpSaWVLYUlaOHZ2NFJLRWdjaHN3T1FhcE8xOE1wSnBuRTk4JTJGQWJGenBROXNMTjdzaGN4bTFtUzhzRFNLTFNLckZpb1VMSDIxVXAzZmRmWThmUmZhcTRjJTJGcjVUSTcwT0ZFeVVGbnJURUM1SGRGM0hVU2E5V2xPcG9zRiUyRmE3dVJXZEZNWEF6VGV4RlQxYW5mNHYwb245M3hmM3k0WmhqT0lNVlp6eW1kJTJGTUkzZW8yNEFuYkE3VzdLUVFEaHM4UGRUZHZlJTJCaTFLMFJwcmolMkZyS0NSRTElMkJ2ZVhOJTJGN2clM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="show_by_usr"></a>5. Show user by user's usr [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/users/admin?sign=" -H "Authorization: Bearer <access_token_returned>"
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
url: http://localhost:8080/resources/users/admin?sign=RFFxVU96VlhZJTJCclNndUVHRGtaaDAlMkJuYW5FbGdVTjhqREZEYmZPOHclMkZiazNxR2VzeTVGMmQ2ZWoyQ3FmbHVZJTJCZVc4RyUyRjl3d1Eya1gzYlRkSjloJTJCSE1RdVJkZVY1b0x6VWRvdlRnWm5KNFNncXZINUc5akxQZm5icEZVT2dLblJiOG1GcnBobmxESEZteklYMTkyS3RueHVWNnFEMEVwUnElMkJ6Ykp2MXlKbmh0RnNhTkhvR1pOdWZ1eFY2dXNJZ0lYYlU4bjF2dERHQ3VYeGZLakh4QldybXhnd1lvQVlYRXNHQzBzaFNsWEtTWk52RjZzTHNNYWRublR5WURXOVJldyUyRmJicGNFdjNoWVBEVHUzajdRMnlYbElGbG03NlE2RjRFWmU4S25wckVvcnVXUTdzRml2ciUyQiUyQjNZZnduejRxd1ZFRWYxdWtqTmdNJTJCUGhVVDVvS00lMkZRJTNEJTNE
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a id="update"></a>6. Update user by user's ID [[TOP]](#index)

```
curl -X POST "http://localhost:8080/resources/users/4" -H "Authorization: bearer <access_token_returned>" -d "usr=tom&name=jerry&pwd=tom"
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
url: http://localhost:8080/resources/users/4
POST
headers: Authorization: bearer <access_token_returned>
payload: usr=tom&name=jerry&pwd=tom
```

#### <a name="delete"></a>7. Delete user by user's ID [[TOP]](#index)

```
curl -X DELETE "http://localhost:8080/resources/users/4?sign=" -H "Authorization: bearer <access_token_returned>"
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
url: http://localhost:8080/resources/users/4?sign=
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