package com.saintdan.framework.config.custom;

import com.saintdan.framework.mapper.UserMapper;
import com.saintdan.framework.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom user details service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 4/22/16
 * @since JDK1.8
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Override public UserDetails loadUserByUsername(String usr) throws UsernameNotFoundException {
    User user = userMapper.findByUsr(usr, 1);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User %s does not exist!", usr));
    }
    return user;
  }

  private final UserMapper userMapper;

  @Autowired
  public CustomUserDetailsService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }
}
