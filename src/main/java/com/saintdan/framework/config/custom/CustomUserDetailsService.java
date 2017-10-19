package com.saintdan.framework.config.custom;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.Assert;
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
    return userRepository.findByUsrAndValidFlag(usr, ValidFlag.VALID).orElseThrow(
        // Throw cannot find any user by this usr param.
        () -> new UsernameNotFoundException(String.format("User %s does not exist!", usr)));
  }

  private final UserRepository userRepository;

  @Autowired public CustomUserDetailsService(UserRepository userRepository) {
    Assert.defaultNotNull(userRepository);
    this.userRepository = userRepository;
  }
}
