/**
 * package of controllers.
 * <p>
 *   Use {@link org.springframework.web.bind.annotation.RestController}
 * </p>
 * <pre>
 *     Example:
 *
 *     OPERATION |     HTTP    |                  URL                  | DESCRIPTION
 *     ----------|-------------|---------------------------------------|---------------
 *     CREATE    | POST        | /users                                | create user
 *     READ      | GET         | /users                                | show all user
 *     READ      | GET         | /users/{id}                           | show user by id
 *     READ      | GET         | /users/{param1},{param2},...,{param n}| show user by param1...
 *     UPDATE    | POST/PUT    | /users/{id}                           | update user by id
 *     DELETE    | DELETE      | /users/{id}                           | delete user by id
 * </pre>
 *
 * <p>
 *   Use {@link com.saintdan.framework.component.SignHelper} to signature.
 *   Use {@link com.saintdan.framework.component.ResultHelper} to return VO.
 * </p>
 * 
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/15
 * @since JDK1.8
 */
package com.saintdan.framework.controller;