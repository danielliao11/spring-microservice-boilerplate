# Spring Rest OAuth2 Sample

This is REST service sample that protected by [Spring OAuth 2](http://projects.spring.io/spring-security-oauth/).

## Usage

Take your token from ```oauth/token``` in terminal:

```
curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app"
```

or [Advanced REST Client](https://github.com/jarrodek/advanced-rest-client) in your Chrome with:

```
url: http://localhost:8080/oauth/token
headers: Authorization: Basic aW9zX2FwcDoxMjM0NTY=
playload: password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app
```

and then, use the **'access_json'** returned to make the authorized request.

```
curl http://localhost:8080/welcome -H "Authorization: Bearer <access_token_returned>"
```

or use Advanced REST Client.

## Version History:

- 0.0.1-SNAPSHOT
  - Initial version.