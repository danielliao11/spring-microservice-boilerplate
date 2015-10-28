package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.exception.ClientException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.service.ClientService;
import com.saintdan.framework.vo.ClientVO;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

/**
 * Implements the
 * {@link ClientService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public class ClientServiceImpl implements ClientService {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new client.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException          CLT0031 Client already existing, name taken.
     */
    @Override
    public ClientVO create(ClientParam param) throws ClientException {
        return null;
    }

    /**
     * Show all clients.
     *
     * @return          clients' VO
     * @throws ClientException          CLT0011 No client exist.
     */
    @Override
    public ObjectsVO getAllClients() throws ClientException {
        return null;
    }

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              roles' page VO
     * @throws ClientException          CLT0011 No client exist.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws ClientException {
        return null;
    }

    /**
     * Show client by id.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException          CLT0012 Cannot find any client by this id param.
     */
    @Override
    public ClientVO getClientById(ClientParam param) throws ClientException {
        return null;
    }

    /**
     * Show client by client id.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException          CLT0011 Cannot find any client by this name param.
     */
    @Override
    public ClientVO getClientByClientId(ClientParam param) throws ClientException {
        return null;
    }

    /**
     * Update client.
     *
     * @param param     client's param
     * @return          client' VO
     * @throws ClientException          CLT0012 Cannot find any client by this id param.
     */
    @Override
    public ClientVO update(ClientParam param) throws ClientException {
        return null;
    }

    /**
     * Delete client
     *
     * @param param     client's param
     * @throws ClientException          CLT0012 Cannot find any client by this id param.
     */
    @Override
    public void delete(ClientParam param) throws ClientException {

    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private ClientService clientService;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private Transformer transformer;

    private final static String CLIENT = "client";


}
