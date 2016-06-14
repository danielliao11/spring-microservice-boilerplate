package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
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

/**
 * Domain of {@link Client}.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
@Service
public class ClientDomain extends BaseDomain<Client, Long> {

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
  public ClientVO create(ClientParam param, User currentUser) throws Exception {
    Client client = clientRepository.findByClientIdAlias(param.getClientIdAlias());
    if (client != null) {
      // Throw client already existing exception, clientId taken.
      throw new CommonsException(ErrorType.SYS0111,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CLIENT_ID));
    }
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
    Client client = clientRepository.findByClientIdAlias(param.getClientIdAlias());
    if (client == null) {
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CLIENT_ID));
    }
    return transformer.po2VO(ClientVO.class, client);
  }

  /**
   * Delete {@link Client}
   *
   * @param currentUser current user
   * @param param       {@link ClientParam}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any client by id param.
   */
  public void delete(ClientParam param, User currentUser) throws Exception {
    Client client = clientRepository.findOne(param.getId());
    if (client == null) {
      // Throw client cannot find by id parameter exception.
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    // Log delete operation.
    logHelper.logUsersOperations(LogType.DELETE, getClassT().getSimpleName(), currentUser);
    // Change valid flag to invalid.
    clientRepository.updateValidFlagFor(ValidFlag.INVALID, client.getId());
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private Transformer transformer;

  private final static String CLIENT_ID = "clientId";

}
