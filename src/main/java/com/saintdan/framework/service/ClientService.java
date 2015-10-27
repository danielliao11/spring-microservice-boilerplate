package com.saintdan.framework.service;

import com.saintdan.framework.exception.ClientException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.vo.ClientVO;
import com.saintdan.framework.vo.ClientsVO;
import com.saintdan.framework.vo.PageVO;
import org.springframework.data.domain.Pageable;

/**
 * Client's service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public interface ClientService {

    /**
     * Create new client.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException
     */
    ClientVO create(ClientParam param) throws ClientException;

    /**
     * Show all clients.
     *
     * @return          clients' VO
     * @throws ClientException
     */
    ClientsVO getAllClients() throws ClientException;

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              roles' page VO
     * @throws ClientException        ROL0011 No role exists.
     */
    PageVO getPage(Pageable pageable) throws ClientException;

    /**
     * Show client by id.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException
     */
    ClientVO getClientById(ClientParam param) throws ClientException;

    /**
     * Show client by client id.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException
     */
    ClientVO getClientByClientId(ClientParam param) throws ClientException;

    /**
     * Update client.
     *
     * @param param     client's param
     * @return          client' VO
     * @throws ClientException
     */
    ClientVO update(ClientParam param) throws ClientException;

    /**
     * Delete client
     *
     * @param param     client's param
     * @throws ClientException
     */
    void delete(ClientParam param) throws ClientException;
}
