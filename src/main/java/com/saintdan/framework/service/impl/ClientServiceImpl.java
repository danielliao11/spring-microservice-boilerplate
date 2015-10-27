package com.saintdan.framework.service.impl;

import com.saintdan.framework.exception.ClientException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.service.ClientService;
import com.saintdan.framework.vo.ClientVO;
import com.saintdan.framework.vo.ClientsVO;
import com.saintdan.framework.vo.PageVO;
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

    @Override
    public ClientVO create(ClientParam param) throws ClientException {
        return null;
    }

    @Override
    public ClientsVO getAllClients() throws ClientException {
        return null;
    }

    @Override
    public PageVO getPage(Pageable pageable) throws ClientException {
        return null;
    }

    @Override
    public ClientVO getClientById(ClientParam param) throws ClientException {
        return null;
    }

    @Override
    public ClientVO getClientByClientId(ClientParam param) throws ClientException {
        return null;
    }

    @Override
    public ClientVO update(ClientParam param) throws ClientException {
        return null;
    }

    @Override
    public void delete(ClientParam param) throws ClientException {

    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------
}
