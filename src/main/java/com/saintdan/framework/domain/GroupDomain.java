package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
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
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Domain of {@link Group}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional
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
  public GroupVO create(GroupParam param, User currentUser) throws Exception {
    Group group = groupRepository.findByName(param.getName());
    if (group != null) {
      // Throw group already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CommonsConstant.NAME));
    }
    return super.createByPO(GroupVO.class, groupParam2PO(param, new Group(), currentUser), currentUser);
  }

  /**
   * Show all {@link GroupVO}.
   *
   * @return {@link ObjectsVO}, {@link GroupVO}
   * @throws CommonsException {@link ErrorType#SYS0121} No group exists.
   */
  public ObjectsVO getAllGroups() throws Exception {
    Iterable groups = groupRepository.findAll();
    if (((List) groups).isEmpty()) {
      // Throw no group exists exception.
      throw new CommonsException(ErrorType.SYS0121,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0121, getClassT().getSimpleName(), getClassT().getSimpleName()));
    }
    return transformer.pos2VO(GroupVO.class, groups);
  }

  /**
   * Show {@link GroupVO} of {@link PageVO}.
   *
   * @param pageable {@link Pageable}
   * @return {@link PageVO}, {@link GroupVO}
   * @throws CommonsException {@link ErrorType#SYS0121} No group exists.
   */
  public PageVO getPage(Pageable pageable) throws Exception {
    Page<Group> groupPage = groupRepository.findAll(pageable);
    if (!groupPage.hasContent()) {
      // Throw no group exists exception.
      throw new CommonsException(ErrorType.SYS0121,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0121, getClassT().getSimpleName(), getClassT().getSimpleName()));
    }
    return transformer.poPage2VO(transformer.poList2VOList(GroupVO.class, groupPage.getContent()), pageable, groupPage.getTotalElements());
  }

  /**
   * Show Iterable<Group> by ids.
   *
   * @param ids ids of groups
   * @return {@link Iterable<Group>}
   * @throws CommonsException {@link ErrorType#SYS0120} No group exists.
   */
  public Iterable<Group> getGroupsByIds(Iterable<Long> ids) throws Exception {
    return groupRepository.findAll(ids);
  }

  /**
   * Show {@link GroupVO} by id of group.
   *
   * @param param {@link GroupParam}
   * @return {@link GroupVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any group by id param.
   */
  public GroupVO getGroupById(GroupParam param) throws Exception {
    Group group = groupRepository.findOne(param.getId());
    if (group == null) {
      // Throw group cannot find by id parameter exception.
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    return transformer.po2VO(GroupVO.class, group);
  }

  /**
   * Show {@link GroupVO} by name of group.
   *
   * @param param {@link GroupParam}
   * @return {@link GroupVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any group by name param.
   */
  public GroupVO getGroupByName(GroupParam param) throws Exception {
    Group group = groupRepository.findByName(param.getName());
    if (group == null) {
      // Throw group cannot find by name parameter exception.
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.NAME));
    }
    return transformer.po2VO(GroupVO.class, group);
  }

  /**
   * Update {@link Group}.
   *
   * @param currentUser current user
   * @param param       {@link GroupParam}
   * @return {@link GroupVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any group by id param.
   */
  public GroupVO update(GroupParam param, User currentUser) throws Exception {
    Group group = groupRepository.findByName(param.getName());
    if (group == null) {
      // Throw cannot find any group by this id param.
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    return super.updateByPO(GroupVO.class, groupParam2PO(param, new Group(), currentUser), currentUser);
  }

  /**
   * Delete {@link Group}.
   *
   * @param currentUser current user
   * @param param       {@link GroupParam}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any group by id param.
   */
  public void delete(GroupParam param, User currentUser) throws Exception {
    Group group = groupRepository.findOne(param.getId());
    if (group == null) {
      // Throw cannot find any group by this id param.
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    // Log delete operation.
    logHelper.logUsersOperations(LogType.DELETE, getClassT().getSimpleName(), currentUser);
    // Change valid flag to invalid.
    groupRepository.updateValidFlagFor(ValidFlag.INVALID, group.getId());
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired
  private RoleDomain roleDomain;

  @Autowired
  private ResourceDomain resourceService;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private Transformer transformer;

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
      Iterable<Resource> resources = resourceService.getResourcesByIds(transformer.idsStr2Iterable(param.getResourceIds()));
      group.setResources(transformer.iterable2Set(resources));
    }
    if (!StringUtils.isBlank(param.getRoleIds())) {
      Iterable<Role> roles = roleDomain.getRolesByIds(transformer.idsStr2Iterable(param.getRoleIds()));
      group.setRoles(transformer.iterable2Set(roles));
    }
    return group;
  }

}
