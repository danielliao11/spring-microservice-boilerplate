package com.saintdan.framework.config.custom;

import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
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
@Service public class CustomUserDetailsService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override public UserDetails loadUserByUsername(String usr) throws UsernameNotFoundException {
    User user = userRepository.findByUsr(usr);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User %s does not exist!", usr));
    }
    return new CustomUserRepositoryUserDetails(user);
  }
}
