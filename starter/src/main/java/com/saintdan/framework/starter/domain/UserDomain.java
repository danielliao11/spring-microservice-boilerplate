package com.saintdan.framework.starter.domain;

import com.saintdan.framework.common.component.CustomPasswordEncoder;
import com.saintdan.framework.common.domain.BaseDomain;
import com.saintdan.framework.starter.mapper.UserMapper;
import com.saintdan.framework.starter.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
@Service
public class UserDomain extends BaseDomain<UserMapper, User> {

  @Override public int create(User user) {
    user.setPwd(passwordEncoder.encode(user.getPwd()));
    return super.create(user);
  }

  private final UserMapper userMapper;
  private final CustomPasswordEncoder passwordEncoder;

  @Autowired
  public UserDomain(UserMapper userMapper, CustomPasswordEncoder passwordEncoder) {
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
    setMapper(userMapper);
  }
}
