package com.saintdan.framework.component;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Custom encrypt utilities.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/28/15
 * @since JDK1.8
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {

  /**
   * Encode the password.
   *
   * @param rawPassword raw password
   * @return encoded password
   */
  @Override public String encode(CharSequence rawPassword) {
    String rawPwd = (String) rawPassword;
    return BCrypt.hashpw(rawPwd, BCrypt.gensalt());
  }

  /**
   * Matches raw password and encoded password.
   *
   * @param rawPassword     raw password
   * @param encodedPassword encoded password
   * @return match or not
   */
  @Override public boolean matches(CharSequence rawPassword, String encodedPassword) {
    String rawPwd = (String) rawPassword;
    return BCrypt.checkpw(rawPwd, encodedPassword);
  }
}
