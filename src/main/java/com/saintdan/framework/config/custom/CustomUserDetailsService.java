package com.saintdan.framework.config.custom;

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
    // TODO find user
    return null;
  }
}
