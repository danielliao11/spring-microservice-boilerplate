package com.saintdan.framework.starter.controller.management;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.controller.CRUDController;
import com.saintdan.framework.common.tools.ResponseHelper;
import com.saintdan.framework.starter.domain.UserAndRoleDomain;
import com.saintdan.framework.starter.domain.UserDomain;
import com.saintdan.framework.starter.po.User;
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
 * Api for {@link User}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/3/31
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.USERS)
public class UserController extends CRUDController<UserDomain, User, String> {

  @PutMapping("/{id}/roles")
  public ResponseEntity updateRoles(@PathVariable("id") String userId, @RequestBody List<UserAndRole> userAndRoleList) {
    userAndRoleDomain.deleteByUserId(userId);
    int result = userAndRoleDomain.batchInsert(userAndRoleList);
    if (result < 0) {
      return ResponseHelper.serverError("Update roles failed.");
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  private final UserDomain userDomain;
  private final UserAndRoleDomain userAndRoleDomain;

  @Autowired
  public UserController(UserDomain userDomain, UserAndRoleDomain userAndRoleDomain) {
    this.userDomain = userDomain;
    this.userAndRoleDomain = userAndRoleDomain;
    setDomain(userDomain);
  }
}
