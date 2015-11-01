package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.exception.ResourceException;
import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.param.GroupParam;
import com.saintdan.framework.param.LogParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.GroupRepository;
import com.saintdan.framework.service.GroupService;
import com.saintdan.framework.service.LogService;
import com.saintdan.framework.service.ResourceService;
import com.saintdan.framework.service.RoleService;
import com.saintdan.framework.tools.SpringSecurityUtils;
import com.saintdan.framework.vo.GroupVO;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implements the
 * {@link GroupService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
public class GroupServiceImpl implements GroupService {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new group.
     *
     * @param currentUser   current user
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0031 Group already existing, name taken.
     * @throws ResourceException     RSC0012 Cannot find any resource by this id param.
     * @throws RoleException         ROL0012 Cannot find any role by this id param.
     */
    @Override
    public GroupVO create(GroupParam param, User currentUser) throws GroupException, ResourceException, RoleException {
        Group group = groupRepository.findByName(param.getName());
        if (group != null) {
            // Throw group already existing, name taken.
            throw new GroupException(ErrorType.GRP0031);
        }
        return groupPO2VO(groupRepository.save(groupParam2PO(param, new Group(), currentUser)),
                String.format(ControllerConstant.CREATE, GROUP));
    }

    /**
     * Show all groups' VO.
     *
     * @return              groups' VO
     * @throws GroupException        GRP0011 No group exist.
     */
    @Override
    public ObjectsVO getAllGroups() throws GroupException {
        List<Group> groups = (List<Group>) groupRepository.findAll();
        if (groups.isEmpty()) {
            // Throw no group exist exception.
            throw new GroupException(ErrorType.GRP0011);
        }
        return groupsPO2VO(groups, String.format(ControllerConstant.INDEX, GROUP));
    }

    /**
     * Show groups' page VO.
     *
     * @param pageable      page
     * @return              groups' page VO
     * @throws GroupException        GRP0011 No group exist.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws GroupException {
        Page<Group> groupPage = groupRepository.findAll(pageable);
        if (groupPage.getContent().isEmpty()) {
            // Throw no group exist exception.
            throw new GroupException(ErrorType.GRP0011);
        }
        return transformer.poPage2VO(
                poList2VOList(groupPage.getContent()),
                pageable, groupPage.getTotalElements(),
                String.format(ControllerConstant.INDEX, GROUP));
    }

    /**
     * Show groups by ids.
     *
     * @param ids           groups' ids
     * @return              groups' PO
     * @throws GroupException        GRP0012 Cannot find any group by this id param.
     */
    @Override
    public Iterable<Group> getGroupsByIds(Iterable<Long> ids) throws GroupException {
        return groupRepository.findAll(ids);
    }

    /**
     * Show group's VO by group's id.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0012 Cannot find any group by this id param.
     */
    @Override
    public GroupVO getGroupById(GroupParam param) throws GroupException {
        Group group = groupRepository.findOne(param.getId());
        if (group == null) {
            // Throw group cannot find by id parameter exception.
            throw new GroupException(ErrorType.GRP0012);
        }
        return groupPO2VO(group, String.format(ControllerConstant.SHOW, GROUP));
    }

    /**
     * Show group's VO by group's name.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0013 Cannot find any group by this name param.
     */
    @Override
    public GroupVO getGroupByName(GroupParam param) throws GroupException {
        Group group = groupRepository.findByName(param.getName());
        if (group == null) {
            // Throw group cannot find by name parameter exception.
            throw new GroupException(ErrorType.GRP0013);
        }
        return groupPO2VO(group, String.format(ControllerConstant.SHOW, GROUP));
    }

    /**
     * Update group.
     *
     * @param currentUser   current user
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        ROL0012 Cannot find any group by this id param.
     * @throws ResourceException     RSC0012 Cannot find any resource by this id param.
     * @throws RoleException         ROL0012 Cannot find any role by this id param.
     */
    @Override
    public GroupVO update(GroupParam param, User currentUser) throws GroupException, ResourceException, RoleException {
        Group group = groupRepository.findOne(param.getId());
        if (group == null) {
            // Throw group cannot find by id parameter exception.
            throw new GroupException(ErrorType.GRP0012);
        }
        return groupPO2VO(groupRepository.save(groupParam2PO(param, group, currentUser)),
                String.format(ControllerConstant.UPDATE, GROUP));
    }

    /**
     * Delete group.
     *
     * @param currentUser   current user
     * @param param         group's params.
     * @throws GroupException        ROL0012 Cannot find any group by this id param.
     */
    @Override
    public void delete(GroupParam param, User currentUser) throws GroupException {
        Group group = groupRepository.findOne(param.getId());
        if (group == null) {
            // Throw role cannot find by id parameter exception.
            throw new GroupException(ErrorType.ROL0012);
        }
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        String clientId = SpringSecurityUtils.getCurrentUsername();
        logService.create(new LogParam(ip, LogType.DELETE, clientId, ResourceConstant.GROUP), currentUser);
        groupRepository.updateValidFlagFor(ValidFlag.INVALID, group.getId());
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private LogService logService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private Transformer transformer;

    private final static String GROUP = "group";

    /**
     * Transform group's param to PO.
     *
     * @param param         group's param
     * @param group         group
     * @param currentUser   currentUser
     * @return              group's PO
     * @throws ResourceException        RSC0012 Cannot find any resource by this id param.
     * @throws RoleException            ROL0012 Cannot find any role by this id param.
     */
    private Group groupParam2PO(GroupParam param, Group group, User currentUser) throws ResourceException, RoleException {
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        String clientId = SpringSecurityUtils.getCurrentUsername();
        // Init createdBy, lastModifiedBy
        Long createdBy, lastModifiedBy;
        // Init createdDate
        Date createdDate = new Date();
        if (group == null) {
            createdBy = currentUser.getId();
            lastModifiedBy = createdBy;
            logService.create(new LogParam(ip, LogType.CREATE, clientId, ResourceConstant.GROUP), currentUser);
        } else {
            createdBy = group.getCreatedBy();
            createdDate = group.getCreatedDate();
            lastModifiedBy = currentUser.getId();
            logService.create(new LogParam(ip, LogType.UPDATE, clientId, ResourceConstant.GROUP), currentUser);
        }
        BeanUtils.copyProperties(param, group);
        group.setCreatedBy(createdBy);
        group.setCreatedDate(createdDate);
        group.setLastModifiedBy(lastModifiedBy);
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

    /**
     * Transform group's PO to VO.
     *
     * @param group         group's PO
     * @param msg           return message
     * @return              group's VO
     */
    private GroupVO groupPO2VO(Group group, String msg) {
        GroupVO vo = new GroupVO();
        BeanUtils.copyProperties(group, vo);
        if (StringUtils.isBlank(msg)) {
            return vo;
        }
        vo.setMessage(msg);
        // Return success result.
        return (GroupVO) resultHelper.sucessResp(vo);
    }

    /**
     * Transform groups' PO to groups VO
     *
     * @param groups    groups' PO
     * @param msg       return message
     * @return          groups' VO
     */
    private ObjectsVO groupsPO2VO(Iterable<Group> groups, String msg) {
        List objList = poList2VOList(groups);
        ObjectsVO vos = transformer.voList2ObjectsVO(objList, msg);
        return (ObjectsVO) resultHelper.sucessResp(vos);
    }

    /**
     * Transform group's PO list to VO list.
     *
     * @param groups    group's PO list
     * @return          group's VO list
     */
    private List<GroupVO> poList2VOList(Iterable<Group> groups) {
        List<GroupVO> groupVOList = new ArrayList<>();
        for (Group group : groups) {
            GroupVO vo = groupPO2VO(group, "");
            groupVOList.add(vo);
        }
        return groupVOList;
    }
}
