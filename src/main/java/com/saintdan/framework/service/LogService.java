package com.saintdan.framework.service;

import com.saintdan.framework.exception.ClientException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.vo.ClientVO;
import com.saintdan.framework.vo.ClientsVO;
import com.saintdan.framework.vo.LogVO;
import com.saintdan.framework.vo.LogsVO;

/**
 * Log's service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public interface LogService {

    /**
     * Create new client.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException
     */
    LogVO create(ClientParam param) throws ClientException;

    /**
     * Show all clients.
     *
     * @return          clients' VO
     * @throws ClientException
     */
    LogsVO getAllLogs() throws ClientException;

    /**
     * Show client by id.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException
     */
    ClientVO getClientById(ClientParam param) throws ClientException;

}
