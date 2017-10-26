package com.saintdan.framework.domain;

import com.google.common.collect.Sets;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.CustomRepository;
import com.saintdan.framework.repo.RoleRepository;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ResourceVO;
import com.saintdan.framework.vo.RoleVO;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
@Service
@Transactional(readOnly = true)
public class RoleDomain extends BaseDomain<Role, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Transactional public RoleVO create(RoleParam param, User currentUser) throws Exception {
    nameExists(param.getName());
    return po2Vo(super.createByPO(param2Po(param, new Role(), currentUser)));
  }

  public List<RoleVO> all() {
    return roleRepository.findAll().stream()
        .map(role -> {
          try {
            return po2Vo(role);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }).collect(Collectors.toList());
  }

  @Transactional public RoleVO update(RoleParam param, User currentUser) throws Exception {
    Role role = findById(param.getId());
    if (role == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    if (StringUtils.isNotBlank(param.getName()) && !param.getName().equals(role.getName())) {
      nameExists(param.getName());
    }
    return po2Vo(super.updateByPO(param2Po(param, role, currentUser)));
  }

  public RoleVO getById(Long id) throws Exception {
    return po2Vo(roleRepository.findById(id).orElse(null));
  }

  public Role findById(Long id) {
    return roleRepository.findById(id).orElse(null);
  }

  @Transactional @Override public void deepDelete(Long id) throws Exception {
    Role role = findById(id);
    if (role == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName().toLowerCase(),
              CommonsConstant.ID));
    }
    roleRepository.delete(role);
  }

  public RoleVO po2Vo(Role role) throws Exception {
    if (role == null) {
      return null;
    }
    RoleVO vo = transformer.po2VO(RoleVO.class, role);
    vo.setResources(role.getResources().stream()
        .map(resource -> {
          try {
            return transformer.po2VO(ResourceVO.class, resource);
          } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }).collect(Collectors.toSet()));
    return vo;
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  private final RoleRepository roleRepository;
  private final ResourceDomain resourceDomain;

  @Autowired public RoleDomain(CustomRepository<Role, Long> repository, Transformer transformer,
      RoleRepository roleRepository, ResourceDomain resourceDomain) {
    super(repository, transformer);
    Assert.defaultNotNull(roleRepository);
    Assert.defaultNotNull(resourceDomain);
    this.roleRepository = roleRepository;
    this.resourceDomain = resourceDomain;
  }

  private Role param2Po(RoleParam param, Role role, User currentUser) throws Exception {
    transformer.param2PO(getClassT(), param, role, currentUser);
    if (StringUtils.isNotBlank(param.getResourceIds())) {
      Set<Resource> resources = Sets
          .newHashSet(resourceDomain.getAllByIds(transformer.idsStr2List(param.getResourceIds())));
      role.setResources(resources);
    } else {
      role.setResources(new HashSet<>());
    }
    return role;
  }

  private void nameExists(String name) throws Exception {
    if (roleRepository.findByNameAndValidFlag(name, ValidFlag.VALID).isPresent()) {
      // Throw role already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName().toLowerCase(),
              CommonsConstant.NAME));
    }
  }
}
