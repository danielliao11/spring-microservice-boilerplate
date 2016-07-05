# spring-rest-oauth2-sample

[中文版](README_zh.md)

## <a name="index"></a>Index

- [Build and Run](#build)
- [Usage](#usage)
  - [Data Format](#data_format)
  - [Use the Seed](#seed)
  - [Get **access_token**](#access_token)
  - [Get New **access_token** with **refresh_token**](#refresh_token)
  - [Access to Welcome Resource](#welcome)
  - [Access to User Resource](#user)
    - [1. Create User](#create)
    - [2. Show All Users](#all)
    - [3. Show Users in Page](#page)
    - [4. Show User by id](#show_by_id)
    - [5. Show User by usr](#show_by_usr)
    - [6. Update User by id](#update)
    - [7. Delete User by id](#delete)
  - [Other Resources](#other)
- [Deploy](#deploy)
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

### <a name="data_format"></a>Data Format [[TOP]](#index)

Data format of response：

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {}
}
```

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

### <a name="refresh_token"></a>Get New **access_token** with **refresh_token** [[TOP]](#index)

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

### <a name="welcome"></a>Access to Welcome Resource [[TOP]](#index)

Use the **access_token** returned to make the authorized request to the protected endpoint:

```
$ curl -X GET http://localhost:8080/welcome -H "Authorization: Bearer <access_token_returned>"
```
If the request is successful, you will see the following JSON response like this:

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 2,
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

### <a name="user"></a>Access to User Resource [[TOP]](#index)

#### <a name="create"></a>1. Create New User [[TOP]](#index)

```
curl -X POST "http://localhost:8080/resources/v1/users" -H "Authorization: bearer <access_token_returned>" -d "usr=tommy&name=tom&pwd=tom12345&sign=WElnaHE3d2hLM3B6MTBqcTZrM2FOOTdUV0N5cldKcDJuTWMxRW1hck55Qk1FbzRxZm9xMVJzdk9ITTFFNjhQZGxqc0k1ZTdDcmJaemwyN2tFckFHZ3ZKeW1wQVpvRFAlMkZOdm9hWDFkU2s2TzNVVE5YME03NzI0ODklMkY0eXpCUk1kVDFKbHczWEE0RDZCeWo5YmdSbDkwT3FGcE9rSlglMkZ2TVJJVCUyQmNzRXQzdDZ2ZkExUmhaS1QwaVJTTHI1Snhwd3U4dThsbDhyNm1BOXAlMkZmcUprUTVnUzQ1WHV3UTRWJTJCS2dKRlVMaXBTV2NiJTJGOE9INTlhRWd0eU1ZSTJjS1cybk1samhZSVN5YmUyZlluSmw4V0RLMVhtaWo5R3NKaVJUMHR6JTJGM25rWldocHZNVVBiSUJPTTc5WUhnJTJGNXlOUnFIS0VudGslMkZBMXAwMkViZ2haSW5FOHF5VVElM0QlM0Q="
```

If the request is successful, you will see the following JSON response like this:

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 4,
    "name": "tom",
    "usr": "tommy",
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

#### 2. <a name="all"></a>Show All Users [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users?sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response like this:

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "objects": [
      {
        "id": 4,
        "name": "tom",
        "usr": "tommy",
        "description": null
      },
      {
        "id": 1,
        "name": "root",
        "usr": "root",
        "description": "root account"
      },
      {
        "id": 3,
        "name": "guest",
        "usr": "guest",
        "description": "guest account"
      },
      {
        "id": 2,
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
url: http://localhost:8080/resources/v1/users?sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="page"></a>3. Show Users in Page [[TOP]](#page)

```
$ curl -X GET "http://localhost:8080/resources/v1/users?pageNo=1&pageSize=20&sortBy=id:asc,name:desc&sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response like this:

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
      "numberOfElements": 4
    }
  }
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/resources/users?pageNo=1&pageSize=20&sortBy=id:asc,name:desc&sign=dWFOOUVDdXFnYmMyUG55TXJ0aVlNbHpEOEdzQ2VpSHFRNVloRlE3ajBlMGUxcHRrYXB2T2RqSjJWdjczQ0tJaFVrJTJGckhwdXEzT0lRdDJadzF3ZnVONzhoV3JBMkNZTjBSV24lMkJtSW95MjJlRDYxOCUyRkpjNWpTWjRGQXBuOGw4Zzl6VExKN3dHVEU0T1VGeU5vS1ZDcldJSmFkT3NPSk0wNFFjWXMyNTd3ZmtHSCUyQnRCTHhXVVJEbjBZUEFwRjd1amtKZ3FUUW83d3o4VmlWdVY1NThnM3BKZ2QlMkIlMkZNWUV2MEpzTmMlMkZKNkRHaGhROWR5Z2VFRExJUHdzZjZ0dkZqVFlvRVFrY1B2WmhQV0p1R0VEMjZSZHRnVFdWTlNObyUyQjVmZU9MSHBENW9TSHVyVXdrM2FtMFlqTUQyRFoyRkdmek91WkI1NzhrWTZIc1VUUGhvU0diajBzdyUzRCUzRA==
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
$ curl -X GET "http://localhost:8080/resources/v1/users/4?sign=ZUNjN3VUMVp4RVV6TnM4WDJocUhRJTJCNmxZZWNsdEZaZ3NXdWJCd1E1RkpTTWVmWUhsazRPRXZuV2hZZnM1MjU2SkRQJTJCUUFQR2hob0VsRmZieiUyRkVZNXl6dEhqa05relUlMkZUS1duc1hGTmp0NCUyRkU1SGxYcUtnQ21VUFp6OG82NVQwMVd6MXRrazVCQW5iY3FKb0xBNVVlY0l4VWhVTkM1dXdFRzUxMUVIeUwxWUZ3TGY4JTJGJTJGVlZ4Q2lqTERVZ1F2WDJ4OW5JVFJuZUVjVVFHTzFMdFhEb25hVGU3OWpSUjJXV0Q0SGZ4QXk1ZDN6MzFlRmgzMUlnRjhwbW9FTU0ya0h0d0VEJTJGUnBvdFZmMHRwZ0R2NVE0aGlaMGhsdlFoNDJrTHElMkJBekZtU3pFNkRRSmU4dHJWUWJ6dHhmJTJGSU04YlJ2TXdxalAxdDJ4UEF6dHB5aTZhUzh5TlElM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response like this:

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 4,
    "name": "tom",
    "usr": "tommy",
    "description": null
  }
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/4?sign=ZUNjN3VUMVp4RVV6TnM4WDJocUhRJTJCNmxZZWNsdEZaZ3NXdWJCd1E1RkpTTWVmWUhsazRPRXZuV2hZZnM1MjU2SkRQJTJCUUFQR2hob0VsRmZieiUyRkVZNXl6dEhqa05relUlMkZUS1duc1hGTmp0NCUyRkU1SGxYcUtnQ21VUFp6OG82NVQwMVd6MXRrazVCQW5iY3FKb0xBNVVlY0l4VWhVTkM1dXdFRzUxMUVIeUwxWUZ3TGY4JTJGJTJGVlZ4Q2lqTERVZ1F2WDJ4OW5JVFJuZUVjVVFHTzFMdFhEb25hVGU3OWpSUjJXV0Q0SGZ4QXk1ZDN6MzFlRmgzMUlnRjhwbW9FTU0ya0h0d0VEJTJGUnBvdFZmMHRwZ0R2NVE0aGlaMGhsdlFoNDJrTHElMkJBekZtU3pFNkRRSmU4dHJWUWJ6dHhmJTJGSU04YlJ2TXdxalAxdDJ4UEF6dHB5aTZhUzh5TlElM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a name="show_by_usr"></a>5. Show User by usr [[TOP]](#index)

```
$ curl -X GET "http://localhost:8080/resources/v1/users?usr=admin&sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response like this:

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 2,
    "name": "admin",
    "usr": "admin",
    "description": "admin account"
  }
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users?usr=admin&sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

#### <a id="update"></a>6. Update User by id [[TOP]](#index)

```
curl -X PUT "http://localhost:8080/resources/v1/users/4" -H "Authorization: bearer <access_token_returned>" -d "usr=tommy&name=jerry&pwd=tom54321&sign=V05GYiUyQkplUlN4eG44ZzRjQlMwMiUyRnlKaklkSmFKNWgwQlUwbWVSSVB2d0NvTHU0MmxETTh2UnV6ckxOSmpONXNZeDNFakdHMDJ2TlVMMk9UdHVXbVdMOXJvN3UlMkY2cWhvcTNuWUtYVkNFVmZnOGclMkJnN1lvV3lBRjBBSTFGWEtCa1ptYkpEcXhpb1cxJTJCY25uYXN4cW91cnJEZEZQY0glMkJ6cHA2RXdtSGVHOHd3YktGOXkyOEZieU4yM2YxRDA4ZE41bE1JaWlzQllYQkhzc0FGSHRzalRDRjlibDAwcFJPaXhJSEFDblpOJTJGMWhiSlNxMGc0Z2prWXFVRzVOMUlPcEhaTUNHT29pJTJGa1BoZHBtSjBZejhoUWtCUkZPRk9JenQ4MGhJcGJxV3pxb3BPTGU4SnAzcWg2c3cwcUJsdVZ1NEhidmdyckZTNlNkRHFhbWVMM1hnWHNEZyUzRCUzRA=="
```

If the request is successful, you will see the following JSON response like this:

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Successfully",
  "data": {
    "id": 4,
    "name": "jerry",
    "usr": "tommy",
    "description": null
  }
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/4
PUT
headers: Authorization: bearer <access_token_returned>
payload: usr=tommy&name=jerry&pwd=tom54321&sign=V05GYiUyQkplUlN4eG44ZzRjQlMwMiUyRnlKaklkSmFKNWgwQlUwbWVSSVB2d0NvTHU0MmxETTh2UnV6ckxOSmpONXNZeDNFakdHMDJ2TlVMMk9UdHVXbVdMOXJvN3UlMkY2cWhvcTNuWUtYVkNFVmZnOGclMkJnN1lvV3lBRjBBSTFGWEtCa1ptYkpEcXhpb1cxJTJCY25uYXN4cW91cnJEZEZQY0glMkJ6cHA2RXdtSGVHOHd3YktGOXkyOEZieU4yM2YxRDA4ZE41bE1JaWlzQllYQkhzc0FGSHRzalRDRjlibDAwcFJPaXhJSEFDblpOJTJGMWhiSlNxMGc0Z2prWXFVRzVOMUlPcEhaTUNHT29pJTJGa1BoZHBtSjBZejhoUWtCUkZPRk9JenQ4MGhJcGJxV3pxb3BPTGU4SnAzcWg2c3cwcUJsdVZ1NEhidmdyckZTNlNkRHFhbWVMM1hnWHNEZyUzRCUzRA==
```

#### <a name="delete"></a>7. Delete User by id [[TOP]](#index)

```
curl -X DELETE "http://localhost:8080/resources/v1/users/4?sign=ZUNjN3VUMVp4RVV6TnM4WDJocUhRJTJCNmxZZWNsdEZaZ3NXdWJCd1E1RkpTTWVmWUhsazRPRXZuV2hZZnM1MjU2SkRQJTJCUUFQR2hob0VsRmZieiUyRkVZNXl6dEhqa05relUlMkZUS1duc1hGTmp0NCUyRkU1SGxYcUtnQ21VUFp6OG82NVQwMVd6MXRrazVCQW5iY3FKb0xBNVVlY0l4VWhVTkM1dXdFRzUxMUVIeUwxWUZ3TGY4JTJGJTJGVlZ4Q2lqTERVZ1F2WDJ4OW5JVFJuZUVjVVFHTzFMdFhEb25hVGU3OWpSUjJXV0Q0SGZ4QXk1ZDN6MzFlRmgzMUlnRjhwbW9FTU0ya0h0d0VEJTJGUnBvdFZmMHRwZ0R2NVE0aGlaMGhsdlFoNDJrTHElMkJBekZtU3pFNkRRSmU4dHJWUWJ6dHhmJTJGSU04YlJ2TXdxalAxdDJ4UEF6dHB5aTZhUzh5TlElM0QlM0Q=" -H "Authorization: bearer <access_token_returned>"
```

If the request is successful, you will see the following JSON response like this:

```
{
  "code": "200",
  "operationStatus": "SUCCESS",
  "message": "Delete user successfully.",
  "data": null
}
```

or use Advanced REST Client:

```
url: http://localhost:8080/resources/v1/users/4?sign=ZUNjN3VUMVp4RVV6TnM4WDJocUhRJTJCNmxZZWNsdEZaZ3NXdWJCd1E1RkpTTWVmWUhsazRPRXZuV2hZZnM1MjU2SkRQJTJCUUFQR2hob0VsRmZieiUyRkVZNXl6dEhqa05relUlMkZUS1duc1hGTmp0NCUyRkU1SGxYcUtnQ21VUFp6OG82NVQwMVd6MXRrazVCQW5iY3FKb0xBNVVlY0l4VWhVTkM1dXdFRzUxMUVIeUwxWUZ3TGY4JTJGJTJGVlZ4Q2lqTERVZ1F2WDJ4OW5JVFJuZUVjVVFHTzFMdFhEb25hVGU3OWpSUjJXV0Q0SGZ4QXk1ZDN6MzFlRmgzMUlnRjhwbW9FTU0ya0h0d0VEJTJGUnBvdFZmMHRwZ0R2NVE0aGlaMGhsdlFoNDJrTHElMkJBekZtU3pFNkRRSmU4dHJWUWJ6dHhmJTJGSU04YlJ2TXdxalAxdDJ4UEF6dHB5aTZhUzh5TlElM0QlM0Q=
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