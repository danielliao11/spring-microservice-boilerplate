# Spring Rest OAuth2 Sample

This is REST service sample that protected by:
- [Spring Boot](http://projects.spring.io/spring-boot/);
- [Spring OAuth 2](http://projects.spring.io/spring-security-oauth/);
- [Spring Security](http://projects.spring.io/spring-security/).

## Build and Run

```
$ cd <spring-rest-oauth2-sample root path>
$ ./gradlew clean build bootRun
```

## Usage

1. Take your token from `oauth/token` in terminal, if you use ssl remember add `-k`:

```
$ curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read&client_secret=123456&client_id=ios_app"
```

or [Advanced REST Client](https://github.com/jarrodek/advanced-rest-client) in your Chrome with:

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
playload: password=admin&username=admin&grant_type=password&scope=read
```

2. Use the **'access_json'** returned to make the authorized request to the protected endpoint:

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

3. Get user information:

```
$ curl "http://localhost:8080/resources/users/usr=admin&sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=" -H "Authorization: Bearer <access_token_returned>"
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
url: http://localhost:8080/resources/users/usr=admin&sign=TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=
GET
headers: Authorization: bearer <access_token_returned>
```

4. Refresh token:

```
curl -X POST -vu ios_app:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=<refresh_token_returned>&client_secret=123456&client_id=ios_app"
```

or use Advanced REST Client:

```
url: http://localhost:8080/oauth/token
POST
headers: Authorization: Basic <Encrypt client_id:client_secret by HTTP Basic>
playload: grant_type=refresh_token&refresh_token=<refresh_token_returned>
```

## License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2015 saintdan

## Version History:

- 0.0.1-SNAPSHOT
  - Initial version.
  
- 0.1.0-RELEASE
  - Release version.
  
- 0.2.0-RELEASE
  - Change authorities to resources.
  - Add https.
  - Fix some bugs.
  
- 0.2.1-RELEASE
  - Add name to Resource PO.
  - Change getAuthorities().
  - Change import.sql.
  
- 0.3.0-RELEASE
  - Add /bo, /enums, /exception.
  - Add UserService and its implement.
  
- 0.3.1-RELEASE
  - Modify build.gradle.
  - Add refresh token usage.
  
- 0.3.2-RELEASE
  - Fix some hidden bugs.
  - Add SystemRuntimeException and UnknownException.
  
- 0.4.0-RELEASE
  - Add RSA signature.(You can generate your own RSA key pair with ssh-keygen, or get it in [GenerateRSAKeyPair](/src/test/java/com/saintdan/framework/GenerateRSAKeyPair.java).
  
- 0.4.1-RELEASE
  - Add [LogUtils](/src/main/java/com/saintdan/framework/tools/LogUtils.java) to trace info, warn, debug, error.
  
- 0.4.2-RELEASE
  - Optimize some codes.
  
- 0.4.3-RELEASE
  - Add [Base64ImageHelper](/src/main/java/com/saintdan/framework/tools/Base64ImageHelper.java).
  
- 0.4.4-RELEASE
  - Fix the error: "Could not find or load main class org.gradle.wrapper.GradleWrapperMain."  
    Add gradle-wrapper.jar
    Thanks [cbweixin](https://github.com/cbweixin) for reminding me.
    
- 0.5.0-RELEASE
  - Extract the elements with similar return results and integrate them into one -> [ResultHelper](/src/main/java/saintdan/framework/component/ResultHelper.java);
  - Extract the elements with similar signature and integrate them into one -> [SignHelper](/src/main/java/saintdan/framework/component/SignHelper.java);
  - Add package of RESTFul parameters.
  - Optimize code of services, implements, controllers.
  
- 0.5.1-RELEASE
  - Add success result response to [ResultHelper](/src/main/java/saintdan/framework/component/ResultHelper.java);
  - Optimize code of user service and controller.
  - Update Spring Boot to 2.0.7.RELEASE
  - Fix the signature bugs, and changes the test sign.
  
- 0.6.0-RELEASE
  - Add Maven support.