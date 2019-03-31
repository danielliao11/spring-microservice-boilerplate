package com.saintdan.framework.starter.domain;

import com.saintdan.framework.common.domain.BaseDomain;
import com.saintdan.framework.starter.mapper.UserAndRoleMapper;
import com.saintdan.framework.starter.po.UserAndRole;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
@Service
public class UserAndRoleDomain extends BaseDomain<UserAndRoleMapper, UserAndRole> {

  public int batchInsert(List<UserAndRole> userAndRoleList) {
    return userAndRoleMapper.insertList(userAndRoleList);
  }

  public int deleteByUserId(String userId) {
    return userAndRoleMapper.deleteByUserId(userId);
  }

  public int deleteByRoleId(String roleId) {
    return userAndRoleMapper.deleteByRoleId(roleId);
  }

  private final UserAndRoleMapper userAndRoleMapper;

  @Autowired
  public UserAndRoleDomain(UserAndRoleMapper userAndRoleMapper) {
    this.userAndRoleMapper = userAndRoleMapper;
    setMapper(userAndRoleMapper);
  }
}
