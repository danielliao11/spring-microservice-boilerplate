package com.saintdan.framework.tools;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Encrypt util.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/28/15
 * @since JDK1.8
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder{

    @Override
    public String encode(CharSequence rawPassword) {
        String rawPwd = (String) rawPassword;
        return BCrypt.hashpw(rawPwd, BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String rawPwd = (String) rawPassword;
        boolean status =  BCrypt.checkpw(rawPwd, encodedPassword);
        return status;
    }
}
