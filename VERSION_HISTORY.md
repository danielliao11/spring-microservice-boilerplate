# VERSION HISTORY

- 0.0.1.SNAPSHOT
  - [ADD] Initial version.
  
- 0.1.0.RELEASE
  - [ADD] Release version.
  
- 0.2.0.RELEASE
  - [ADD] Add https.
  - [MOD] Change authorities to resources.
  - [FIX] Fix some bugs.
  
- 0.2.1.RELEASE
  - [ADD] Add name to Resource PO.
  - [MOD] Change getAuthorities().
  - [MOD] Change import.sql.
  
- 0.3.0.RELEASE
  - [ADD] Add /bo, /enums, /exception.
  - [ADD] Add UserService and its implement.
  
- 0.3.1.RELEASE
  - [ADD] Add refresh token usage.
  - [MOD] Modify build.gradle.

- 0.3.2.RELEASE
  - [ADD] Add SystemRuntimeException and UnknownException.
  - [FIX] Fix some hidden bugs.

- 0.4.0.RELEASE
  - [ADD] Add RSA signature.(You can generate your own RSA key pair with ssh-keygen, or get it in [GenerateRSAKeyPair](/src/test/java/com/saintdan/framework/GenerateRSAKeyPair.java).
  
- 0.4.1.RELEASE
  - [ADD] Add [LogUtils](/src/main/java/com/saintdan/framework/tools/LogUtils.java) to trace info, warn, debug, error.
  
- 0.4.2.RELEASE
  - [MOD] Optimize some codes.
  
- 0.4.3.RELEASE
  - [ADD] Add [Base64ImageHelper](/src/main/java/com/saintdan/framework/tools/Base64ImageHelper.java).
  
- 0.4.4.RELEASE
  - [FIX] Fix the error: "Could not find or load main class org.gradle.wrapper.GradleWrapperMain."
    Add gradle-wrapper.jar
    Thanks [cbweixin](https://github.com/cbweixin) for reminding me.
    
- 0.5.0.RELEASE
  - [ADD] Add package of RESTFul parameters.
  - [MOD] Extract the elements with similar return results and integrate them into one --> [ResultHelper](/src/main/java/saintdan/framework/component/ResultHelper.java);
  - [MOD] Extract the elements with similar signature and integrate them into one --> [SignHelper](/src/main/java/saintdan/framework/component/SignHelper.java);
  - [MOD] Optimize error of services, implements, controllers.
  
- 0.5.1.RELEASE
  - [ADD] Add success result response to [ResultHelper](/src/main/java/saintdan/framework/component/ResultHelper.java);
  - [MOD] Optimize error of user loginService and controller.
  - [MOD] Update Spring Boot to 2.0.7.RELEASE
  - [FIX] Fix the signature bugs, and changes the test sign.
  
- 0.6.0.RELEASE
  - [ADD] Add Maven support.
  
- 0.7.0.RELEASE
  - [ADD] Add MySql support.
  
- 0.8.0.RELEASE
  - [ADD] Add CRUD of user, role, group, resource.
  - [ADD] Add some components, constants, exceptions.
  - [ADD] Add [Seed](src/test/java/com/saintdan/framework/repo/Seed.java).
  
- 0.8.1.RELEASE
  - [MOD] Modify table's name.
  
- 0.8.2.RELEASE
  - [ADD] Add [CustomClientDetailsService](src/main/java/com/saintdan/framework/config/custom/CustomClientDetailsService.java), add jdbc client choice.
  - [ADD] Add createdBy, createdDate, lastModifiedBy, lastModifiedAt to POs.
  - [ADD] Add [SpringSecurityUtils](src/main/java/com/saintdan/framework/tools/SpringSecurityUtils.java), trace user's ip address.
  - [MOD] Modify [Seed](src/test/java/com/saintdan/framework/repo/Seed.java).
  
- 0.8.3.RELEASE
  - [ADD] Add show all resources in page.
  - [ADD] Add log resource, hide the update & delete interface.
  - [MOD] Extract the elements with similar xxxsVO into one -> [ObjectsVO](src/main/java/com/saintdan/framework/vo/ObjectsVO.java).
  
- 0.8.4.RELEASE
  - [ADD] Add valid flag. When you don't want to delete something, you can set the valid flag to 'invalid'.
  - [ADD] Add [RepositoryWithoutDelete](src/main/java/com/saintdan/framework/repo/RepositoryWithoutDelete.java) to hide delete interface.

- 0.8.5.RELEASE
  - [ADD] Add [@CurrentUser](src/main/java/com/saintdan/framework/annotation/CurrentUser.java), annotate param with it can get current login user.
  - [ADD] Add some error comments.
  - [MOD] Separate the SignField to SignField and [ValidationField](src/main/java/com/saintdan/framework/annotation/ValidationField.java).

- 0.9.0.RELEASE
  - [ADD] Add [ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java), can validate current user, param, sign.
  - [ADD] Add [CommonsException](src/main/java/com/saintdan/framework/exception/CommonsException.java) and modify the [ErrorType](src/main/java/com/saintdan/framework/enums/ErrorType.java)

- 0.9.1.RELEASE
  - [ADD] Use @Valid and BindingResult to validate param.
  - [MOD] Modify the [ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java).
  - [DEL] Remove the @ValidateField.

- 0.9.2.RELEASE
  - [ADD] Add PostgreSQL support.
  - [MOD] Change project to DDD.

- 0.9.3.RELEASE
  - [MOD] Change the log of login.
  - [FIX] Fix antMatcher bug.
  - [DEL] Delete some outdated classes and codes.

- 0.9.4.RELEASE
  - [ADD] Add login with authorization_code.

- 0.9.5.RELEASE
  - [ADD] Add devtools support.
  - [MOD] Replace commons-logging with slf4j.

- 0.9.6.RELEASE
  - [MOD] Improve the performance of Spring Boot.
    - [DEL] Remove `JmxAutoConfiguration.class` and `WebSocketAutoConfiguration.class` import.
    - [DEL] Remove `spring-boot-starter-tomcat`, replace with `spring-boot-starter-undertow`.

- 0.9.7.RELEASE
  - [FIX] Fix some bugs.
  - [DEL] Delete redundant codes of domain.
  - [DEL] Delete update valid flag which is in the [RepositoryWithoutDelete](src/main/java/com/saintdan/framework/repo/RepositoryWithoutDelete.java).

- 0.10.0.RELEASE
  - [ADD] Add refresh token support.
  - [ADD] Add docker support.
  - [ADD] Add log.
  - [MOD] Change StringBuild to String.join
  - [FIX] Fix some bugs.

- 0.10.1.RELEASE
  - [MOD] Modify VO.
  - [MOD] Modify domain, controller.
  - [MOD] Modify [Transformer](src/main/java/com/saintdan/framework/component/Transformer.java)
  - [MOD] Modify [ResultHelper](src/main/java/com/saintdan/framework/component/ResultHelper.java)
  - [FIX] Fix some bugs.

- 0.10.2.RELEASE
  - [MOD] Change error style to google error style.
  - [MOD] Modify transaction.
  - [MOD] Optimize codes.
  - [FIX] Fix bugs.

- 0.10.3.RELEASE
  - [ADD] Add Optional for PO.
  - [MOD] Optimize codes.
  - [FIX] Fix bug of error error_description.

- 0.11.1.RELEASE
  - [MOD] Use Stream and Optional.
  - [FIX] Fix sequence bug.

- 0.11.2.RELEASE
  - [MOD] Modify #showAll(), #showPage()
  - [MOD] Modify README.

- 0.12.1.RELEASE
  - [ADD] Add [specification-arg-resolver](https://github.com/tkaczmarzyk/specification-arg-resolver) for filter.
  - [ADD] Add `validateWithOutSignCheck` of [ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java)
  - [MOD] Modify `validate` to `validateWithSignCheck` of [ValidateHelper](src/main/java/com/saintdan/framework/component/ValidateHelper.java)
  - [DEL] Delete ObjectsVO.java and PageVO.java.
  - [DEL] Delete redundant error.

- 0.12.2.RELEASE
  - [DEL] Delete redundant error.
  - [FIX] Fix some bugs.

- 0.13.0.RELEASE
  - [ADD] ADD [NotNullField](src/main/java/com/saintdan/framework/annotation/NotNullField.java)
  - [MOD] Modify validate helper and BaseDomain.
  - [FIX] Fix bugs.

- 0.13.1.RELEASE
  - [ADD] Add init.sql;
  - [MOD] Modify base domain.

- 0.13.2.RELEASE
  - [FIX] Fix validate bugs.
  - [FIX] Fix update bugs.
  - [FIX] Fix valid bugs.

- 0.14.0.RELEASE
  - [MOD] Upgrade spring boot to 1.4.x

- 0.14.1.RELEASE
  - [MOD] Change param null validate.

- 0.14.2.RELEASE
  - [MOD] Modify build.gradle, application.properties, init.sql, logback.xml.
  - [MOD] Regenerate pom.xml
  - [MOD] Replace deprecated APIs.
  - [DEL] Delete Seed.

- 0.15.0.RELEASE
  - [MOD] Change java.util.date to java.time

- 0.16.0.RELEASE
  - [MOD] Change custom response to standard response entity.
  - [MOD] Change ResultVO -> ErrorVO.
  - [DEL] Remove some useless codes.
  
- 0.16.1.RELEASE
  - [DEL] Remove successResp().
  
- 0.16.2.RELEASE
  - [DEL] Delete useless param of get access token request in read me.
  - [FIX] Fix typo error in init.sql.
  
- 0.16.3.RELEASE
  - [MOD] Modify generate pom task in build.gradle.
  - [DEL] Delete fastjson.
  
- 0.17.0.RELEASE
  - [MOD] Add login valid.
  
- 0.17.1.RELEASE
  - [MOD] Modify sequence generator.
  
- 0.18.0.RELEASE
  - [ADD] Add swagger.
  - [ADD] Add login and refresh token api.
  - [MOD] Change params of API to json.
  - [FIX] Fix client, user, role, resource bugs.
  
- 0.18.1.RELEASE
  - [MOD] Modify field injection to constructor injection.
  - [MOD] Override POJO toString().
  - [MOD] Use guava split.

- 0.18.2.RELEASE
  - [ADD] Add spring boot admin support.
  
- 1.0.0.RELEASE
  - [ADD] Add lombok.
  - [MOD] Modify PO.
  - [DEL] Delete some useless codes.
  
- 1.1.0.RELEASE
  - [ADD] Add request config bean.
  - [ADD] Add limit filter.
  - [MOD] Modify ValidateHelper to ValidateFilter.
  - [DEL] Delete validate in controller.

- 1.2.0.RELEASE
  - [ADD] Add LogInterceptor.
  - [DEL] Delete log logic in domain.
  
- 1.2.1.RELEASE
  - [FIX] Fix validate bug.

- 1.2.2.RELEASE
  - [MOD] Upgrade spring boot to 2.0.
  - [DEL] Delete some deprecated code.
  - [FIX] Fix uri bug.