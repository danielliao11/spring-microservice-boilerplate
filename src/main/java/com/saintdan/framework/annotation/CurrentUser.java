package com.saintdan.framework.annotation;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Get current user.{@link AuthenticationPrincipal}
 *
 * <pre>
 * @Controller
 * public class MyController {
 *   @RequestMapping("/user/current/show")
 *   public String show(@CurrentUser CustomUser customUser) {
 *       // do something with CustomUser
 *       return "view";
 *   }
 * </pre>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/29/15
 * @since JDK1.8
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {

}
