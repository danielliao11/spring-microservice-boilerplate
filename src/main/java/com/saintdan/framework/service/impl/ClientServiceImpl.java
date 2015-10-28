package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.ClientException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.Client;
import com.saintdan.framework.repo.ClientRepository;
import com.saintdan.framework.service.ClientService;
import com.saintdan.framework.vo.ClientVO;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the
 * {@link ClientService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
@Service
public class ClientServiceImpl implements ClientService {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new client.
     *
     * @param param     client's param
     * @return          client's VO
     * @throws ClientException          CLT0031 Client already existing, clientId taken.
     */
    @Override
    public ClientVO create(ClientParam param) throws ClientException {
        Client client = clientRepository.findByClientIdAlias(param.getClientIdAlias());
        if (client != null) {
            // Throw Resource already existing, clientId taken.
            throw new ClientException(ErrorType.CLT0031);
        }
        return clientPO2VO(clientRepository.save(clientParam2PO(param)),
                String.format(ControllerConstant.CREATE, CLIENT));
    }

    /**
     * Show all clients.
     *
     * @return          clients' VO
     * @throws ClientException          CLT0011 No client exist.
     */
    @Override
    public ObjectsVO getAllClients() throws ClientException {
        List<Client> clients = (List<Client>) clientRepository.findAll();
        if (clients.isEmpty()) {
            // Throw no client exist exception.
            throw new ClientException(ErrorType.CLT0011);
        }
        return clientsPO2VO(clients, String.format(ControllerConstant.INDEX, CLIENT));
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
        Page<Client> clientPage = clientRepository.findAll(pageable);
        if (clientPage.getContent().isEmpty()) {
            // Throw no client exist exception.
            throw new ClientException(ErrorType.CLT0011);
        }
        return transformer.poPage2VO(
                poList2VOList(clientPage.getContent()),
                pageable, clientPage.getTotalElements(),
                String.format(ControllerConstant.INDEX, CLIENT));
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
        Client client = clientRepository.findOne(param.getId());
        if (client == null) {
            // Throw client cannot find by id parameter exception.
            throw new ClientException(ErrorType.GRP0012);
        }
        return clientPO2VO(client, String.format(ControllerConstant.SHOW, CLIENT));
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
        Client client = clientRepository.findByClientIdAlias(param.getClientIdAlias());
        if (client == null) {
            throw new ClientException(ErrorType.CLT0011);
        }
        return clientPO2VO(client, String.format(ControllerConstant.SHOW, CLIENT));
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
        if (!clientRepository.exists(param.getId())) {
            // Throw client cannot find by id parameter exception.
            throw new ClientException(ErrorType.CLT0012);
        }
        return clientPO2VO(clientRepository.save(clientParam2PO(param)),
                String.format(ControllerConstant.UPDATE, CLIENT));
    }

    /**
     * Delete client
     *
     * @param param     client's param
     * @throws ClientException          CLT0012 Cannot find any client by this id param.
     */
    @Override
    public void delete(ClientParam param) throws ClientException {
        Client client = clientRepository.findOne(param.getId());
        if (client == null) {
            // Throw client cannot find by id parameter exception.
            throw new ClientException(ErrorType.CLT0012);
        }
        clientRepository.delete(client);
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private Transformer transformer;

    private final static String CLIENT = "client";

    /**
     * Transform client's param to PO.
     *
     * @param param         client's param
     * @return              client's PO
     */
    private Client clientParam2PO(ClientParam param) {
        Client client = new Client();
        BeanUtils.copyProperties(param, client);
        return client;
    }

    /**
     * Transform client's PO to VO.
     *
     * @param client      client's PO
     * @param msg           return message
     * @return              client's VO
     */
    private ClientVO clientPO2VO(Client client, String msg) {
        ClientVO vo = new ClientVO();
        BeanUtils.copyProperties(client, vo);
        if (StringUtils.isBlank(msg)) {
            return vo;
        }
        vo.setMessage(msg);
        // Return success result.
        return (ClientVO) resultHelper.sucessResp(vo);
    }

    /**
     * Transform clients' PO to clients VO
     *
     * @param clients     clients' PO
     * @param msg           return message
     * @return              clients' VO
     */
    private ObjectsVO clientsPO2VO(Iterable<Client> clients, String msg) {
        List objList = poList2VOList(clients);
        ObjectsVO vos = transformer.voList2ObjectsVO(objList, msg);
        return (ObjectsVO) resultHelper.sucessResp(vos);
    }

    /**
     * Transform client's PO list to VO list.
     *
     * @param clients     client's PO list
     * @return              client's VO list
     */
    private List<ClientVO> poList2VOList(Iterable<Client> clients) {
        List<ClientVO> clientVOList = new ArrayList<>();
        for (Client client : clients) {
            ClientVO vo = clientPO2VO(client, "");
            clientVOList.add(vo);
        }
        return clientVOList;
    }
}
