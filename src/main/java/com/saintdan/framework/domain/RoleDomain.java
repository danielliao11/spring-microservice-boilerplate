package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.RoleRepository;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.RoleVO;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link Role}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service @Transactional(readOnly = true) public class RoleDomain extends BaseDomain<Role, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Transactional public RoleVO create(RoleParam param, User currentUser) throws Exception {
    nameExists(param.getName());
    return super.createByPO(RoleVO.class, roleParam2PO(param, new Role(), currentUser), currentUser);
  }

  @Transactional public RoleVO update(RoleParam param, User currentUser) throws Exception {
    Role role = findById(param.getId());
    if (param.getName().equals(role.getName())) {
      nameExists(param.getName());
    }
    return super.updateByPO(RoleVO.class, roleParam2PO(param, role, currentUser), currentUser);
  }

  public Role findById(Long id) {
    return roleRepository.findById(id).orElse(null);
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private UserDomain userDomain;

  @Autowired private ResourceDomain resourceDomain;

  @Autowired private RoleRepository roleRepository;

  @Autowired private Transformer transformer;

  private Role roleParam2PO(RoleParam param, Role role, User currentUser) throws Exception {
    transformer.param2PO(getClassT(), param, role, currentUser);
    if (!StringUtils.isBlank(param.getUserIds())) {
      List<User> users = userDomain.getAllByIds(transformer.idsStr2List(param.getUserIds()));
      role.setUsers(transformer.list2Set(users));
    }
    if (!StringUtils.isBlank(param.getResourceIds())) {
      Iterable<Resource> resources = resourceDomain.getAllByIds(transformer.idsStr2List(param.getResourceIds()));
      role.setResources((Set<Resource>) resources);
    }
    return role;
  }

  private void nameExists(String name) throws Exception {
    if (roleRepository.findByNameAndValidFlag(name, ValidFlag.VALID).isPresent()) {
      // Throw role already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.NAME));
    }
  }

}
