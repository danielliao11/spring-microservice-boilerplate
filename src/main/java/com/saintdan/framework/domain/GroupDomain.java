package com.saintdan.framework.domain;

import com.saintdan.framework.exception.CommonsException;
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
public interface GroupDomain extends BaseDomain<Group, Long> {

    /**
     * Create new group.
     *
     * @param currentUser   current user
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException        SYS0111 group already existing, name taken.
     */
    GroupVO create(GroupParam param, User currentUser) throws Exception;

    /**
     * Show all groups' VO.
     *
     * @return              groups' VO
     * @throws CommonsException        SYS0120 No group exists.
     */
    ObjectsVO getAllGroups() throws Exception;

    /**
     * Show groups' page VO.
     *
     * @param pageable      page
     * @return              groups' page VO
     * @throws CommonsException        SYS0120 No group exists.
     */
    PageVO getPage(Pageable pageable) throws Exception;

    /**
     * Show groups by ids.
     *
     * @param ids           groups' ids
     * @return              groups' PO
     * @throws CommonsException        SYS0120 No group exists.
     */
    Iterable<Group> getGroupsByIds(Iterable<Long> ids) throws Exception;

    /**
     * Show group's VO by group's id.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException        SYS0122 Cannot find any group by id param.
     */
    GroupVO getGroupById(GroupParam param) throws Exception;

    /**
     * Show group's VO by group's name.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException        SYS0122 Cannot find any group by id param.
     */
    GroupVO getGroupByName(GroupParam param) throws Exception;

    /**
     * Update group.
     *
     * @param currentUser   current user
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException        SYS0122 Cannot find any group by id param.
     */
    GroupVO update(GroupParam param, User currentUser) throws Exception;

    /**
     * Delete group.
     *
     * @param currentUser   current user
     * @param param         group's params.
     * @throws CommonsException        SYS0122 Cannot find any group by id param.
     */
    void delete(GroupParam param, User currentUser) throws Exception;

}
