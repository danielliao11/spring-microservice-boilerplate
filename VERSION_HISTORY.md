# VERSION HISTORY

- 0.0.1.SNAPSHOT
  - Initial version.
  
- 0.1.0.RELEASE
  - Release version.
  
- 0.2.0.RELEASE
  - Change authorities to resources.
  - Add https.
  - Fix some bugs.
  
- 0.2.1.RELEASE
  - Add name to Resource PO.
  - Change getAuthorities().
  - Change import.sql.
  
- 0.3.0.RELEASE
  - Add /bo, /enums, /exception.
  - Add UserService and its implement.
  
- 0.3.1.RELEASE
  - Modify build.gradle.
  - Add refresh token usage.
  
- 0.3.2.RELEASE
  - Fix some hidden bugs.
  - Add SystemRuntimeException and UnknownException.
  
- 0.4.0.RELEASE
  - Add RSA signature.(You can generate your own RSA key pair with ssh-keygen, or get it in [GenerateRSAKeyPair](/src/test/java/com/saintdan/framework/GenerateRSAKeyPair.java).
  
- 0.4.1.RELEASE
  - Add [LogUtils](/src/main/java/com/saintdan/framework/tools/LogUtils.java) to trace info, warn, debug, error.
  
- 0.4.2.RELEASE
  - Optimize some codes.
  
- 0.4.3.RELEASE
  - Add [Base64ImageHelper](/src/main/java/com/saintdan/framework/tools/Base64ImageHelper.java).
  
- 0.4.4.RELEASE
  - Fix the error: "Could not find or load main class org.gradle.wrapper.GradleWrapperMain."  
    Add gradle-wrapper.jar
    Thanks [cbweixin](https://github.com/cbweixin) for reminding me.
    
- 0.5.0.RELEASE
  - Extract the elements with similar return results and integrate them into one --> [ResultHelper](/src/main/java/saintdan/framework/component/ResultHelper.java);
  - Extract the elements with similar signature and integrate them into one --> [SignHelper](/src/main/java/saintdan/framework/component/SignHelper.java);
  - Add package of RESTFul parameters.
  - Optimize code of services, implements, controllers.
  
- 0.5.1.RELEASE
  - Add success result response to [ResultHelper](/src/main/java/saintdan/framework/component/ResultHelper.java);
  - Optimize code of user service and controller.
  - Update Spring Boot to 2.0.7.RELEASE
  - Fix the signature bugs, and changes the test sign.
  
- 0.6.0.RELEASE
  - Add Maven support.
  
- 0.7.0.RELEASE
  - Add MySql support.
  
- 0.8.0.RELEASE
  - Add CRUD of user, role, group, resource.
  - Add some components, constants, exceptions.
  - Add [Seed](src/test/java/com/saintdan/framework/repo/Seed.java).
  
- 0.8.1.RELEASE
  - Modify table's name.
  
- 0.8.2.RELEASE
  - Add [CustomClientDetailsService](src/main/java/com/saintdan/framework/config/custom/CustomClientDetailsService.java), add jdbc client choice.
  - Add createdBy, createdDate, lastModifiedBy, lastModifiedDate to POs.
  - Add [SpringSecurityUtils](src/main/java/com/saintdan/framework/tools/SpringSecurityUtils.java), trace user's ip address.
  - Modify [Seed](src/test/java/com/saintdan/framework/repo/Seed.java).
  
- 0.8.3.RELEASE
  - Extract the elements with similar xxxsVO into one -> [ObjectsVO](src/main/java/com/saintdan/framework/vo/ObjectsVO.java).
  - Add show all resources in page.
  - Add log resource, hide the update & delete interface.
  
- 0.8.4.RELEASE
  - Add valid flag. When you don't want to delete something, you can set the valid flag to 'invalid'.
  - Add [RepositoryWithoutDelete](src/main/java/com/saintdan/framework/repo/RepositoryWithoutDelete.java) to hide delete interface.

- 0.8.5.RELEASE
  - Add [@CurrentUser](src/main/java/com/saintdan/framework/annotation/CurrentUser.java), annotate param with it can get current login user.
  - Separate the SignField to SignField and [ValidationField](src/main/java/com/saintdan/framework/annotation/ValidationField.java).
  - Add some code comments.

- 0.9.0.RELEASE
  - Add [ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java), can validate current user, param, sign.
  - Add [CommonsException](src/main/java/com/saintdan/framework/exception/CommonsException.java) and modify the [ErrorType](src/main/java/com/saintdan/framework/enums/ErrorType.java)

- 0.9.1.RELEASE
  - Modify the [ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java).
  - Remove the @ValidateField.
  - Use @Valid and BindingResult to validate param.

- 0.9.2.RELEASE
  - Change project to DDD.
  - Add PostgreSQL support.

- 0.9.3.RELEASE
  - Fix antMatcher bug.
  - Change the log of login.
  - Delete some outdated classes and codes.

- 0.9.4.RELEASE
  - Add login with authorization_code.

- 0.9.5.RELEASE
  - Add devtools support.
  - Replace commons-logging with slf4j.

- 0.9.6.RELEASE
  - Improve the performance of Spring Boot.
    - Remove `JmxAutoConfiguration.class` and `WebSocketAutoConfiguration.class` import.
    - Remove `spring-boot-starter-tomcat`, replace with `spring-boot-starter-undertow`.

- 0.9.7.RELEASE
  - Delete redundant codes of domain.
  - Delete update valid flag which is in the [RepositoryWithoutDelete](src/main/java/com/saintdan/framework/repo/RepositoryWithoutDelete.java).
  - Fix some bugs.

- 0.10.0.RELEASE
  - Add refresh token support.
  - Fix some bugs.