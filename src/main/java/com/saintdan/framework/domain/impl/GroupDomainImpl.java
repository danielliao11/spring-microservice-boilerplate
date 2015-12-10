package com.saintdan.framework.domain.impl;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
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
import com.saintdan.framework.domain.GroupDomain;
import com.saintdan.framework.domain.ResourceDomain;
import com.saintdan.framework.domain.RoleDomain;
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
 * Implements the
 * {@link GroupDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional
public class GroupDomainImpl extends BaseDomainImpl<Group, Long> implements GroupDomain {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new group.
     *
     * @param currentUser   current user
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException        SYS0111 role already existing, name taken.
     */
    @Override
    public GroupVO create(GroupParam param, User currentUser) throws Exception {
        Group group = groupRepository.findByName(param.getName());
        if (group != null) {
            // Throw group already existing exception, name taken.
            throw new CommonsException(ErrorType.SYS0111,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.NAME));
        }
        return super.createByPO(GroupVO.class, groupParam2PO(param, new Group(), currentUser), currentUser);
    }

    /**
     * Show all groups' VO.
     *
     * @return              groups' VO
     * @throws CommonsException        SYS0120 No group exists.
     */
    @Override
    public ObjectsVO getAllGroups() throws Exception {
        Iterable groups = groupRepository.findAll();
        if (((List) groups).isEmpty()) {
            // Throw no group exists exception.
            throw new CommonsException(ErrorType.SYS0120,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.pos2VO(ObjectsVO.class, groups, String.format(ControllerConstant.INDEX, getClassT()));
    }

    /**
     * Show groups' page VO.
     *
     * @param pageable      page
     * @return              groups' page VO
     * @throws CommonsException        SYS0120 No group exists.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws Exception {
        Page<Group> groupPage = groupRepository.findAll(pageable);
        if (groupPage.getContent().isEmpty()) {
            // Throw no group exists exception.
            throw new CommonsException(ErrorType.SYS0120,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.poPage2VO(transformer.poList2VOList(GroupVO.class, groupPage.getContent()), pageable, groupPage.getTotalElements(),
                String.format(ControllerConstant.INDEX, getClassT()));
    }

    /**
     * Show groups by ids.
     *
     * @param ids           groups' ids
     * @return              groups' PO
     * @throws CommonsException        SYS0120 No group exists.
     */
    @Override
    public Iterable<Group> getGroupsByIds(Iterable<Long> ids) throws Exception {
        return groupRepository.findAll(ids);
    }

    /**
     * Show group's VO by group's id.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException        SYS0122 Cannot find any group by id param.
     */
    @Override
    public GroupVO getGroupById(GroupParam param) throws Exception {
        Group group = groupRepository.findOne(param.getId());
        if (group == null) {
            // Throw group cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return transformer.po2VO(GroupVO.class, group, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Show group's VO by group's name.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException        SYS0122 Cannot find any group by name param.
     */
    @Override
    public GroupVO getGroupByName(GroupParam param) throws Exception {
        Group group = groupRepository.findByName(param.getName());
        if (group == null) {
            // Throw group cannot find by name parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.NAME));
        }
        return transformer.po2VO(GroupVO.class, group, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Update group.
     *
     * @param currentUser   current user
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException        SYS0122 Cannot find any group by id param.
     */
    @Override
    public GroupVO update(GroupParam param, User currentUser) throws Exception {
        Group group = groupRepository.findByName(param.getName());
        if (group == null) {
            // Throw cannot find any group by this id param.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return super.updateByPO(GroupVO.class, groupParam2PO(param, new Group(), currentUser), currentUser);
    }

    /**
     * Delete group.
     *
     * @param currentUser   current user
     * @param param         group's params.
     * @throws CommonsException        SYS0122 Cannot find any group by id param.
     */
    @Override
    public void delete(GroupParam param, User currentUser) throws Exception {
        Group group = groupRepository.findOne(param.getId());
        if (group == null) {
            // Throw cannot find any group by this id param.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
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
    private RoleDomain roleService;

    @Autowired
    private ResourceDomain resourceService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private Transformer transformer;

    /**
     * Transform group's param to PO.
     *
     * @param param         group's param
     * @param group         group
     * @param currentUser   currentUser
     * @return              group's PO
     */
    private Group groupParam2PO(GroupParam param, Group group, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, group, currentUser);
        if (!StringUtils.isBlank(param.getResourceIds())) {
            Iterable<Resource> resources = resourceService.getResourcesByIds(transformer.idsStr2Iterable(param.getResourceIds()));
            group.setResources(transformer.iterable2Set(resources));
        }
        if (!StringUtils.isBlank(param.getRoleIds())) {
            Iterable<Role> roles = roleService.getRolesByIds(transformer.idsStr2Iterable(param.getRoleIds()));
            group.setRoles(transformer.iterable2Set(roles));
        }
        return group;
    }

}
