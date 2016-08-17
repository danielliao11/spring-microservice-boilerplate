package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ResourceConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Group;
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

  /**
   * Create new {@link Role}.
   *
   * @param currentUser current user
   * @param param       {@link RoleParam}
   * @return {@link RoleVO}
   * @throws CommonsException {@link ErrorType#SYS0111} user already existing, usr taken.
   */
  @Transactional public RoleVO create(RoleParam param, User currentUser) throws Exception {
    if (roleRepository.findByNameAndValidFlag(param.getName(), ValidFlag.VALID).isPresent()) {
      // Throw role already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.NAME));
    }
    return super.createByPO(RoleVO.class, roleParam2PO(param, new Role(), currentUser), currentUser);
  }

  /**
   * Get {@link RoleVO} by name of role.
   *
   * @param param {@link RoleParam}
   * @return {@link RoleVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any role by name param.
   */
  public RoleVO getRoleByName(RoleParam param) throws Exception {
    return transformer.po2VO(RoleVO.class, findByName(param.getName()));
  }

  public Role findByName(String name) throws Exception {
    return roleRepository.findByNameAndValidFlag(name, ValidFlag.VALID).orElseThrow(
        () -> new CommonsException(ErrorType.SYS0122,
            ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, ResourceConstant.ROLES, CommonsConstant.NAME)));
  }

  /**
   * Update {@link Role}.
   *
   * @param param {@link RoleParam}
   * @return {@link RoleVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any role by id param.
   */
  @Transactional public RoleVO update(RoleParam param, User currentUser) throws Exception {
    findById(param.getId());
    return super.updateByPO(RoleVO.class, roleParam2PO(param, new Role(), currentUser), currentUser);
  }

  public Role findById(Long id) throws Exception {
    return roleRepository.findById(id).orElseThrow(
        () -> new CommonsException(ErrorType.SYS0122,
            ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, ResourceConstant.ROLES, CommonsConstant.ID)));
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private UserDomain userDomain;

  @Autowired private GroupDomain groupService;

  @Autowired private RoleRepository roleRepository;

  @Autowired private Transformer transformer;

  /**
   * Transform {@link RoleParam} to {@link Role}.
   *
   * @param param       {@link RoleParam}
   * @param role        {@link Role}
   * @param currentUser currentUser
   * @return {@link Role}
   */
  private Role roleParam2PO(RoleParam param, Role role, User currentUser) throws Exception {
    transformer.param2PO(getClassT(), param, role, currentUser);
    if (!StringUtils.isBlank(param.getUserIds())) {
      List<User> users = userDomain.getAllByIds(transformer.idsStr2List(param.getUserIds()));
      role.setUsers(transformer.list2Set(users));
    }
    if (!StringUtils.isBlank(param.getGroupIds())) {
      Iterable<Group> groups = groupService.getAllByIds(transformer.idsStr2List(param.getGroupIds()));
      role.setGroups((Set<Group>) groups);
    }
    return role;
  }

}
