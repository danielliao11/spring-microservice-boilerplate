package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.enums.ErrorType;
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

  @Transactional public ClientVO create(ClientParam param, User currentUser) throws Exception {
    clientIdExists(param.getClientIdAlias());
    return super.createByPO(ClientVO.class, transformer.param2PO(getClassT(), param, new Client(), currentUser), currentUser);
  }

  public ClientVO getClientByClientId(ClientParam param) throws Exception {
    return transformer.po2VO(ClientVO.class, findClientByClientId(param.getClientIdAlias()));
  }

  public Client findById(Long id) {
    return clientRepository.findById(id).orElse(null);
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private ClientRepository clientRepository;

  @Autowired private Transformer transformer;

  private final static String CLIENT_ID = "clientId";

  private Client findClientByClientId(String clientId) {
    return clientRepository.findByClientIdAliasAndValidFlag(clientId, ValidFlag.VALID).orElse(null);
  }

  private void clientIdExists(String clientId) throws Exception {
    if (clientRepository.findByClientIdAliasAndValidFlag(clientId, ValidFlag.VALID).isPresent()) {
      // Throw client already existing exception, clientId taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CLIENT_ID));
    }
  }

}
