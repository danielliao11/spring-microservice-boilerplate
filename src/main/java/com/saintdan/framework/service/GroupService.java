package com.saintdan.framework.service;

import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.exception.ResourceException;
import com.saintdan.framework.exception.RoleException;
import com.saintdan.framework.param.GroupParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.GroupVO;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.springframework.data.domain.Pageable;

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
     * @param currentUser   current user
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0031 Group already existing, name taken.
     * @throws ResourceException     RSC0012 Cannot find any resource by this id param.
     * @throws RoleException         ROL0012 Cannot find any role by this id param.
     */
    GroupVO create(GroupParam param, User currentUser) throws GroupException, ResourceException, RoleException;

    /**
     * Show all groups' VO.
     *
     * @return              groups' VO
     * @throws GroupException        GRP0011 No group exist.
     */
    ObjectsVO getAllGroups() throws GroupException;

    /**
     * Show groups' page VO.
     *
     * @param pageable      page
     * @return              groups' page VO
     * @throws GroupException        GRP0011 No group exist.
     */
    PageVO getPage(Pageable pageable) throws GroupException;

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
     * @throws GroupException        GRP0013 Cannot find any group by this name param.
     */
    GroupVO getGroupByName(GroupParam param) throws GroupException;

    /**
     * Update group.
     *
     * @param currentUser   current user
     * @param param         group's params
     * @return              group's VO
     * @throws GroupException        GRP0012 Cannot find any group by this id param.
     * @throws ResourceException     RSC0012 Cannot find any resource by this id param.
     * @throws RoleException         ROL0012 Cannot find any role by this id param.
     */
    GroupVO update(GroupParam param, User currentUser) throws GroupException, ResourceException, RoleException;

    /**
     * Delete group.
     *
     * @param currentUser   current user
     * @param param         group's params.
     * @throws GroupException        GRP0012 Cannot find any group by this id param.
     */
    void delete(GroupParam param, User currentUser) throws GroupException;

}
