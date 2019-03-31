package com.saintdan.framework.starter.domain;

import com.saintdan.framework.common.domain.BaseDomain;
import com.saintdan.framework.starter.mapper.RoleMapper;
import com.saintdan.framework.starter.po.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/2/26
 * @since JDK1.8
 */
@Service
public class RoleDomain extends BaseDomain<RoleMapper, Role> {

  private final RoleMapper roleMapper;

  @Autowired
  public RoleDomain(RoleMapper roleMapper) {
    this.roleMapper = roleMapper;
    setMapper(roleMapper);
  }
}
