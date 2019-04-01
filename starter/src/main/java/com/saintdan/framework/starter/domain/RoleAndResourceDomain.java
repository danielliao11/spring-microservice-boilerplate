package com.saintdan.framework.starter.domain;

import com.saintdan.framework.starter.mapper.RoleAndResourceMapper;
import com.saintdan.framework.starter.po.RoleAndResource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
@Service
public class RoleAndResourceDomain {

  public int batchInsert(List<RoleAndResource> roleAndResourceList) {
    return roleAndResourceMapper.insertList(roleAndResourceList);
  }

  public int deleteByRoleId(String roleId) {
    return roleAndResourceMapper.deleteByRoleId(roleId);
  }

  public int deleteByResourceId(String resourceId) {
    return roleAndResourceMapper.deleteByResourceId(resourceId);
  }

  private final RoleAndResourceMapper roleAndResourceMapper;

  @Autowired
  public RoleAndResourceDomain(RoleAndResourceMapper roleAndResourceMapper) {
    this.roleAndResourceMapper = roleAndResourceMapper;
  }
}
