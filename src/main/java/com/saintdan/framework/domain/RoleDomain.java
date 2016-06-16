package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.RoleRepository;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.RoleVO;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link Role}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
public class RoleDomain extends BaseDomain<Role, Long> {

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
    Role role = roleRepository.findByName(param.getName());
    if (role != null) {
      // Throw role already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.NAME));
    }
    return super.createByPO(RoleVO.class, roleParam2PO(param, new Role(), currentUser), currentUser);
  }

  /**
   * Show all {@link RoleVO}.
   *
   * @return roles
   * @throws CommonsException {@link ErrorType#SYS0121} No role exists.
   */
  public ObjectsVO getAllRoles() throws Exception {
    Iterable roles = roleRepository.findAll();
    if (((List) roles).isEmpty()) {
      // Throw no role exists exception.
      throw new CommonsException(ErrorType.SYS0121,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0121, getClassT().getSimpleName(), getClassT().getSimpleName()));
    }
    return transformer.pos2VO(RoleVO.class, roles);
  }

  /**
   * Show {@link RoleVO} in {@link PageVO}.
   *
   * @param pageable page
   * @return {@link RoleVO}
   * @throws CommonsException {@link ErrorType#SYS0121} No role exists.
   */
  public PageVO getPage(Pageable pageable) throws Exception {
    Page<Role> rolePage = roleRepository.findAll(pageable);
    if (!rolePage.hasContent()) {
      // Throw no role exists exception.
      throw new CommonsException(ErrorType.SYS0121,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0121, getClassT().getSimpleName(), getClassT().getSimpleName()));
    }
    return transformer.poPage2VO(transformer.poList2VOList(RoleVO.class, rolePage.getContent()), pageable, rolePage.getTotalElements());
  }

  /**
   * Show Iterable<Role> by ids of roles.
   *
   * @param ids roles' ids
   * @return roles' PO
   * @throws CommonsException {@link ErrorType#SYS0120} No role exists.
   */
  public Iterable<Role> getRolesByIds(Iterable<Long> ids) throws Exception {
    return roleRepository.findAll(ids);
  }

  /**
   * Show {@link RoleVO} by id of role.
   *
   * @param param {@link RoleParam}
   * @return {@link RoleVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any role by id param.
   */
  public RoleVO getRoleById(RoleParam param) throws Exception {
    Role role = roleRepository.findOne(param.getId());
    if (role == null) {
      // Throw role cannot find by id parameter exception.
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    return transformer.po2VO(RoleVO.class, role);
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

  /**
   * Update {@link Role}.
   *
   * @param param {@link RoleParam}
   * @return {@link RoleVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any role by id param.
   */
  @Transactional public RoleVO update(RoleParam param, User currentUser) throws Exception {
    findByName(param.getName());
    return super.updateByPO(RoleVO.class, roleParam2PO(param, new Role(), currentUser), currentUser);
  }

  /**
   * Delete {@link Role}.
   *
   * @param currentUser current user
   * @param param       {@link RoleParam}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any role by id param.
   */
  @Transactional public void delete(RoleParam param, User currentUser) throws Exception {
    Role role = roleRepository.findOne(param.getId());
    if (role == null) {
      // Throw cannot find any role by this id param.
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    // Log delete operation.
    logHelper.logUsersOperations(LogType.DELETE, getClassT().getSimpleName(), currentUser);
    // Change valid flag to invalid.
    roleRepository.updateValidFlagFor(ValidFlag.INVALID, role.getId());
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
      Iterable<User> roles = userDomain.getUsersByIds(transformer.idsStr2Iterable(param.getUserIds()));
      role.setUsers(transformer.iterable2Set(roles));
    }
    if (!StringUtils.isBlank(param.getGroupIds())) {
      Iterable<Group> groups = groupService.getGroupsByIds(transformer.idsStr2Iterable(param.getGroupIds()));
      role.setGroups((Set<Group>) groups);
    }
    return role;
  }

  /**
   * Find {@link Role} by name
   *
   * @param name      name of {@link Role}
   * @return          {@link Role}
   * @throws Exception
   */
  private Role findByName(String name) throws Exception {
    Role role = roleRepository.findByName(name);
    if (role == null) {
      // Throw role cannot find by name parameter exception.
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.NAME));
    }
    return role;
  }

}
