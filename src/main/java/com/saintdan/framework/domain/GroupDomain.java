package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ResourceConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.GroupParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.GroupRepository;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.GroupVO;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link Group}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
public class GroupDomain extends BaseDomain<Group, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create new {@link Group}.
   *
   * @param currentUser current user
   * @param param       {@link GroupParam}
   * @return {@link GroupVO}
   * @throws CommonsException {@link ErrorType#SYS0111} role already existing, name taken.
   */
  @Transactional public GroupVO create(GroupParam param, User currentUser) throws Exception {
    if (groupRepository.findByName(param.getName()).isPresent()) {
      // Throw group already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.NAME));
    }
    return super.createByPO(GroupVO.class, groupParam2PO(param, new Group(), currentUser), currentUser);
  }

  /**
   * Show {@link GroupVO} by name of group.
   *
   * @param param {@link GroupParam}
   * @return {@link GroupVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any group by name param.
   */
  public GroupVO getGroupByName(GroupParam param) throws Exception {
    return transformer.po2VO(GroupVO.class, findByName(param.getName()));
  }

  public Group findByName(String name) throws Exception {
    return groupRepository.findByName(name).orElseThrow(
        () -> new CommonsException(ErrorType.SYS0122,
            ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, ResourceConstant.GROUPS, CommonsConstant.NAME)));
  }

  /**
   * Update {@link Group}.
   *
   * @param currentUser current user
   * @param param       {@link GroupParam}
   * @return {@link GroupVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any group by id param.
   */
  @Transactional public GroupVO update(GroupParam param, User currentUser) throws Exception {
    findById(param.getId());
    return super.updateByPO(GroupVO.class, groupParam2PO(param, new Group(), currentUser), currentUser);
  }

  /**
   * Delete {@link Group}.
   *
   * @param currentUser current user
   * @param id          {@link Group#id}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any group by id param.
   */
  @Transactional public void delete(Long id, User currentUser) throws Exception {
    Group group = findById(id);
    // Log delete operation.
    logHelper.logUsersOperations(OperationType.DELETE, getClassT().getSimpleName(), currentUser);
    // Change valid flag to invalid.
    groupRepository.updateValidFlagFor(ValidFlag.INVALID, group.getId());
  }

  public Group findById(Long id) throws Exception {
    return groupRepository.findById(id).orElseThrow(
        () -> new CommonsException(ErrorType.SYS0122,
            ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, ResourceConstant.GROUPS, CommonsConstant.ID)));
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private RoleDomain roleDomain;

  @Autowired private ResourceDomain resourceService;

  @Autowired private GroupRepository groupRepository;

  @Autowired private Transformer transformer;

  /**
   * Transform group's param to PO.
   *
   * @param param       {@link GroupParam}
   * @param group       {@link Group}
   * @param currentUser currentUser
   */
  private Group groupParam2PO(GroupParam param, Group group, User currentUser) throws Exception {
    transformer.param2PO(getClassT(), param, group, currentUser);
    if (!StringUtils.isBlank(param.getResourceIds())) {
      List<Resource> resources = resourceService.getAllByIds(transformer.idsStr2List(param.getResourceIds()));
      group.setResources(transformer.list2Set(resources));
    }
    if (!StringUtils.isBlank(param.getRoleIds())) {
      List<Role> roles = roleDomain.getAllByIds(transformer.idsStr2List(param.getRoleIds()));
      group.setRoles(transformer.list2Set(roles));
    }
    return group;
  }

}
