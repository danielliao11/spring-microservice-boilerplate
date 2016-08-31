package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ResourceConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.Client;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.ClientRepository;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link Client}.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
@Service @Transactional(readOnly = true) public class ClientDomain extends BaseDomain<Client, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create new {@link Client}.
   *
   * @param currentUser current user
   * @param param       {@link ClientParam}
   * @return {@link ClientVO}
   * @throws CommonsException {@link ErrorType#SYS0111} client already existing, name taken.
   */
  @Transactional public ClientVO create(ClientParam param, User currentUser) throws Exception {
    clientIdExists(param.getClientIdAlias());
    return super.createByPO(ClientVO.class, transformer.param2PO(getClassT(), param, new Client(), currentUser), currentUser);
  }

  /**
   * Show client by client id.
   *
   * @param param {@link ClientParam}
   * @return {@link ClientVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any client by name param.
   */
  public ClientVO getClientByClientId(ClientParam param) throws Exception {
    return transformer.po2VO(ClientVO.class, findClientByClientId(param.getClientIdAlias()));
  }

  /**
   * Delete {@link Client}
   *
   * @param currentUser current user
   * @param id          {@link Client#id}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any client by id param.
   */
  @Transactional public void delete(Long id, User currentUser) throws Exception {
    Client client = findById(id);
    // Log delete operation.
    logHelper.logUsersOperations(OperationType.DELETE, getClassT().getSimpleName(), currentUser);
    // Change valid flag to invalid.
    clientRepository.updateValidFlagFor(ValidFlag.INVALID, client.getId());
  }

  public Client findById(Long id) throws Exception {
    return clientRepository.findById(id).orElseThrow(
        () -> new CommonsException(ErrorType.SYS0122,
            ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, ResourceConstant.CLIENTS, CommonsConstant.ID)));
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private ClientRepository clientRepository;

  @Autowired private Transformer transformer;

  private final static String CLIENT_ID = "clientId";

  private Client findClientByClientId(String clientId) throws Exception {
    return clientRepository.findByClientIdAlias(clientId).orElseThrow(
        () -> new CommonsException(ErrorType.SYS0122,
            ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, ResourceConstant.CLIENTS, CLIENT_ID)));
  }

  private void clientIdExists(String clientId) throws Exception {
    if (clientRepository.findByClientIdAlias(clientId).isPresent()) {
      // Throw client already existing exception, clientId taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CLIENT_ID));
    }
  }

}
