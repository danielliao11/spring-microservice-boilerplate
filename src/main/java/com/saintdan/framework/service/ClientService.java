package com.saintdan.framework.service;

import com.saintdan.framework.exception.ClientException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.vo.ClientVO;
import com.saintdan.framework.vo.ObjectsVO;
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
     * @throws ClientException          CLT0031 Client already existing, name taken.
     */
    ClientVO create(ClientParam param) throws ClientException;

    /**
     * Show all clients.
     *
     * @return          clients' VO
     * @throws ClientException          CLT0011 No client exist.
     */
    ObjectsVO getAllClients() throws ClientException;

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              roles' page VO
     * @throws ClientException          CLT0011 No client exist.
     */
    PageVO getPage(Pageable pageable) throws ClientException;

    /**
     * Show client by id.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException          CLT0012 Cannot find any client by this id param.
     */
    ClientVO getClientById(ClientParam param) throws ClientException;

    /**
     * Show client by client id.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException          CLT0011 Cannot find any client by this name param.
     */
    ClientVO getClientByClientId(ClientParam param) throws ClientException;

    /**
     * Update client.
     *
     * @param param     client's param
     * @return          client' VO
     * @throws ClientException          CLT0012 Cannot find any client by this id param.
     */
    ClientVO update(ClientParam param) throws ClientException;

    /**
     * Delete client
     *
     * @param param     client's param
     * @throws ClientException          CLT0012 Cannot find any client by this id param.
     */
    void delete(ClientParam param) throws ClientException;
}
