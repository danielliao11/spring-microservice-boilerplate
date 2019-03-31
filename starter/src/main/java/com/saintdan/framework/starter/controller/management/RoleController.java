package com.saintdan.framework.starter.controller.management;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.controller.CRUDController;
import com.saintdan.framework.common.tools.ResponseHelper;
import com.saintdan.framework.starter.domain.RoleAndResourceDomain;
import com.saintdan.framework.starter.domain.RoleDomain;
import com.saintdan.framework.starter.domain.UserAndRoleDomain;
import com.saintdan.framework.starter.po.Role;
import com.saintdan.framework.starter.po.RoleAndResource;
import com.saintdan.framework.starter.po.UserAndRole;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api for {@link Role}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/3/31
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.ROLES)
public class RoleController extends CRUDController<RoleDomain, Role, String> {

  @PutMapping("/{id}/users")
  public ResponseEntity updateUsers(@PathVariable("id") String roleId, @RequestBody List<UserAndRole> userAndRoleList) {
    userAndRoleDomain.deleteByRoleId(roleId);
    int result = userAndRoleDomain.batchInsert(userAndRoleList);
    if (result < 0) {
      return ResponseHelper.serverError("Update users failed.");
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}/resources")
  public ResponseEntity updateResources(@PathVariable("id") String roleId, @RequestBody List<RoleAndResource> roleAndResourceList) {
    roleAndResourceDomain.deleteByRoleId(roleId);
    int result = roleAndResourceDomain.batchInsert(roleAndResourceList);
    if (result < 0) {
      return ResponseHelper.serverError("Update resources failed.");
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  private final RoleDomain roleDomain;
  private final UserAndRoleDomain userAndRoleDomain;
  private final RoleAndResourceDomain roleAndResourceDomain;

  @Autowired
  public RoleController(RoleDomain roleDomain, UserAndRoleDomain userAndRoleDomain, RoleAndResourceDomain roleAndResourceDomain) {
    this.roleDomain = roleDomain;
    this.userAndRoleDomain = userAndRoleDomain;
    this.roleAndResourceDomain = roleAndResourceDomain;
    setDomain(roleDomain);
  }
}
