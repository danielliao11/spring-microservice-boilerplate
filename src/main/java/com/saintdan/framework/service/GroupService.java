package com.saintdan.framework.service;

import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.param.GroupParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.vo.GroupVO;
import com.saintdan.framework.vo.GroupsVO;

/**
 * Group's Service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface GroupService {

    /**
     * Create new group.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0031 Group already existing, name taken.
     */
    GroupVO create(GroupParam param) throws GroupException;

    /**
     * Show all groups' VO.
     *
     * @return              groups' VO
     * @throws GroupException        GRP0011 No group exist.
     */
    GroupsVO getAllGroups() throws GroupException;

    /**
     * Show groups by ids.
     *
     * @param ids           groups' ids
     * @return              groups' PO
     * @throws GroupException        GRP0012 Cannot find any group by this id param.
     */
    Iterable<Group> getGroupsByIds(Iterable<Long> ids) throws GroupException;

    /**
     * Show group's VO by group's id.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0012 Cannot find any group by this id param.
     */
    GroupVO getGroupById(GroupParam param) throws GroupException;

    /**
     * Show group's VO by group's name.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0011 Cannot find any group by this name param.
     */
    GroupVO getGroupByName(GroupParam param) throws GroupException;

    /**
     * Update group.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0012 Cannot find any group by this id param.
     */
    GroupVO update(GroupParam param) throws GroupException;

    /**
     * Delete group.
     *
     * @param param         group's params.
     * @throws GroupException        GRP0012 Cannot find any group by this id param.
     */
    void delete(GroupParam param) throws GroupException;

}
