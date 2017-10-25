package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.AuthorityConstant;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.Client;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.ClientRepository;
import com.saintdan.framework.repo.CustomRepository;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ClientVO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
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
@Service
@Transactional(readOnly = true)
public class ClientDomain extends BaseDomain<Client, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Transactional public ClientVO create(ClientParam param, User currentUser) throws Exception {
    return po2Vo(super.createByPO(param2Po(param, currentUser)));
  }

  public List<ClientVO> all() {
    return clientRepository.findAll().stream().map(this::po2Vo).collect(Collectors.toList());
  }

  public Client findClientByClientId(String clientId) {
    return clientRepository.findByClientIdAlias(clientId).orElse(null);
  }

  public Client findById(Long id) {
    return clientRepository.findById(id).orElse(null);
  }

  @Transactional public ClientVO update(ClientParam param, User currentUser) throws Exception {
    Client client = findById(param.getId());
    if (client == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName().toLowerCase(),
              CommonsConstant.ID));
    }
    return po2Vo(super.updateByPO(param2Po(param, currentUser)));
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  private final ClientRepository clientRepository;

  @Autowired public ClientDomain(CustomRepository<Client, Long> repository, Transformer transformer,
      ClientRepository clientRepository) {
    super(repository, transformer);
    Assert.defaultNotNull(clientRepository);
    this.clientRepository = clientRepository;
  }

  private Client param2Po(ClientParam param, User currentUser) throws Exception {
    Client client = transformer.param2PO(getClassT(), param, new Client(), currentUser);
    if (client.getId() == 0) {
      RandomStringGenerator clientGenerator = new RandomStringGenerator.Builder()
          .withinRange('0', 'z').filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
          .build();
      client.setClientIdAlias(clientGenerator.generate(16));
      client.setClientSecretAlias(clientGenerator.generate(32));
      client.setResourceIdStr(AuthorityConstant.RESOURCE_ID);
      client.setScopeStr(
          StringUtils.isBlank(param.getScope()) ? AuthorityConstant.SCOPE : param.getScope());
      client.setAuthorizedGrantTypeStr(
          StringUtils.isBlank(param.getGrantType()) ? AuthorityConstant.GRANT_TYPE
              : param.getGrantType());
      client.setAuthoritiesStr(AuthorityConstant.AUTHORITY);
    }
    return client;
  }

  private ClientVO po2Vo(Client client) {
    if (client == null) {
      return null;
    }
    return ClientVO.builder()
        .id(client.getId())
        .clientId(client.getClientIdAlias())
        .clientSecret(client.getClientSecretAlias())
        .resourceIds(transformer.str2Set(client.getResourceIdStr()))
        .scope(transformer.str2Set(client.getScopeStr()))
        .authorizedGrantTypes(transformer.str2Set(client.getAuthorizedGrantTypeStr()))
        .registeredRedirectUri(transformer.str2Set(client.getRegisteredRedirectUriStr()))
        .grantedAuthorities(Arrays.stream(client.getAuthoritiesStr().split(CommonsConstant.COMMA))
            .map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
        .accessTokenValiditySeconds(client.getAccessTokenValiditySecondsAlias())
        .refreshTokenValiditySeconds(client.getRefreshTokenValiditySecondsAlias())
        .publicKey(client.getPublicKey())
        .build();
  }
}
