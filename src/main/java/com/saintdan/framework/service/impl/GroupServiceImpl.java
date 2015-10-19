package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.param.GroupParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.repo.GroupRepository;
import com.saintdan.framework.service.GroupService;
import com.saintdan.framework.vo.GroupVO;
import com.saintdan.framework.vo.GroupsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0031 Group already existing, name taken.
     */
    @Override
    public GroupVO create(GroupParam param) throws GroupException {
        Group group = groupRepository.findByName(param.getName());
        if (group != null) {
            // Throw group already existing, name taken.
            throw new GroupException(ErrorType.GRP0031);
        }
        return groupPO2VO(groupRepository.save(groupParam2PO(param)),
                String.format(ControllerConstant.CREATE, GROUP));
    }

    /**
     * Show all groups' VO.
     *
     * @return              groups' VO
     * @throws GroupException        ROL0011 No group exist.
     */
    @Override
    public GroupsVO getAllGroups() throws GroupException {
        List<Group> groups = (List<Group>) groupRepository.findAll();
        if (groups.isEmpty()) {
            // Throw no group exist exception.
            throw new GroupException(ErrorType.GRP0011);
        }
        return groupsPO2VO(groups, String.format(ControllerConstant.INDEX, GROUP));
    }

    /**
     * Show group's VO by group's id.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        ROL0012 Cannot find any group by this id param.
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
     * @throws GroupException        GRP0011 Cannot find any group by this name param.
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
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        ROL0012 Cannot find any group by this id param.
     */
    @Override
    public GroupVO update(GroupParam param) throws GroupException {
        Group group = groupRepository.findOne(param.getId());
        if (group == null) {
            // Throw group cannot find by id parameter exception.
            throw new GroupException(ErrorType.GRP0012);
        }
        return groupPO2VO(groupRepository.save(groupParam2PO(param)),
                String.format(ControllerConstant.UPDATE, GROUP));
    }

    /**
     * Delete group.
     *
     * @param param         group's params.
     * @throws GroupException        ROL0012 Cannot find any group by this id param.
     */
    @Override
    public void delete(GroupParam param) throws GroupException {
        Group group = groupRepository.findOne(param.getId());
        if (group == null) {
            // Throw role cannot find by id parameter exception.
            throw new GroupException(ErrorType.ROL0012);
        }
        groupRepository.delete(group);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ResultHelper resultHelper;

    private final static String GROUP = "group";

    /**
     * Transform group's param to PO.
     *
     * @param param         group's param
     * @return              group's PO
     */
    private Group groupParam2PO(GroupParam param) {
        Group group = new Group();
        BeanUtils.copyProperties(param, group);
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
    private GroupsVO groupsPO2VO(Iterable<Group> groups, String msg) {
        GroupsVO vos = new GroupsVO();
        List<GroupVO> groupVOList = new ArrayList<>();
        for (Group group : groups) {
            GroupVO vo = groupPO2VO(group, "");
            groupVOList.add(vo);
        }
        vos.setGroupVOList(groupVOList);
        if (StringUtils.isBlank(msg)) {
            return vos;
        }
        vos.setMessage(msg);
        return (GroupsVO) resultHelper.sucessResp(vos);
    }
}
