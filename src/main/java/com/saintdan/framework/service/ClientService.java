package com.saintdan.framework.service;

import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.GroupParam;
import com.saintdan.framework.po.Client;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.GroupVO;

/**
 * Client's service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public interface ClientService extends BaseService<Client, Long> {

    /**
     * Show group's VO by group's name.
     *
     * @param param         group's params
     * @return              group's VO
     * @throws CommonsException         SYS0111 group already existing, name taken.
     */
    GroupVO getGroupByName(GroupParam param) throws Exception;

    /**
     * Delete group.
     *
     * @param currentUser   current user
     * @param param         group's params.
     * @throws CommonsException        SYS0111 group already existing, name taken.
     */
    void delete(GroupParam param, User currentUser) throws Exception;
}
