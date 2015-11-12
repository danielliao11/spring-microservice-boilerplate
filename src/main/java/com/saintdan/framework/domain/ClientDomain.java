package com.saintdan.framework.domain;

import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.Client;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.ClientVO;

/**
 * Client's service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public interface ClientDomain extends BaseDomain<Client, Long> {

    /**
     * Create new client.
     *
     * @param currentUser   current user
     * @param param         client's params
     * @return              client's VO
     * @throws CommonsException        SYS0111 client already existing, clientId taken.
     */
    ClientVO create(ClientParam param, User currentUser) throws Exception;

    /**
     * Show client's VO by client's name.
     *
     * @param param         client's params
     * @return              client's VO
     * @throws CommonsException         SYS0111 client already existing, clientId taken.
     */
    ClientVO getClientByClientId(ClientParam param) throws Exception;

    /**
     * Delete group.
     *
     * @param currentUser   current user
     * @param param         client's params.
     * @throws CommonsException        SYS0111 client already existing, name taken.
     */
    void delete(ClientParam param, User currentUser) throws Exception;
}
