package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    return po2Vo(super.createByPO(transformer.param2PO(getClassT(), param, new Client(), currentUser), currentUser));
  }

  public List<ClientVO> getAll() {
    return clientRepository.findAll().stream().map(this::po2Vo).collect(Collectors.toList());
  }

  public Client findClientByClientId(String clientId) {
    return clientRepository.findByClientIdAliasAndValidFlag(clientId, ValidFlag.VALID).orElse(null);
  }

  public Client findById(Long id) {
    return clientRepository.findById(id).orElse(null);
  }

  @Transactional public ClientVO update(ClientParam param, User currentUser) throws Exception {
    Client client = findById(param.getId());
    if (client == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    if (StringUtils.isNotBlank(param.getClientIdAlias())) {
      param.setClientIdAlias(null);
    }
    return po2Vo(super.updateByPO(transformer.param2PO(getClassT(), param, client, currentUser), currentUser));
  }

  @Transactional public void deepDelete(ClientParam param, User currentUser) throws Exception {
    logHelper.logUsersOperations(OperationType.DELETE, getClassT().getName(), currentUser);
    clientRepository.delete(param.getId());
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private ClientRepository clientRepository;

  @Autowired private Transformer transformer;

  private final static String CLIENT_ID = "clientId";

  private void clientIdExists(String clientId) throws Exception {
    if (clientRepository.findByClientIdAliasAndValidFlag(clientId, ValidFlag.VALID).isPresent()) {
      // Throw client already existing exception, clientId taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), CLIENT_ID));
    }
  }

  private ClientVO po2Vo(Client client) {
    if (client == null) {
      return null;
    }
    ClientVO vo = new ClientVO();
    vo.setId(client.getId());
    vo.setClientId(client.getClientIdAlias());
    vo.setClientSecret(client.getClientSecretAlias());
    vo.setResourceIds(transformer.str2Set(client.getResourceIdStr()));
    vo.setScope(transformer.str2Set(client.getScopeStr()));
    vo.setAuthorizedGrantTypes(transformer.str2Set(client.getAuthorizedGrantTypeStr()));
    vo.setRegisteredRedirectUri(transformer.str2Set(client.getRegisteredRedirectUriStr()));
    vo.setGrantedAuthorities(Arrays.stream(client.getAuthoritiesStr().split(CommonsConstant.COMMA))
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    vo.setAccessTokenValiditySeconds(client.getAccessTokenValiditySecondsAlias());
    vo.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySecondsAlias());
    vo.setPublicKey(client.getPublicKey());
    return vo;
  }

}
