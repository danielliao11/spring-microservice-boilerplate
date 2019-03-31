package com.saintdan.framework.starter.domain;

import com.saintdan.framework.common.domain.BaseDomain;
import com.saintdan.framework.starter.mapper.ClientMapper;
import com.saintdan.framework.starter.po.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
@Service
public class ClientDomain extends BaseDomain<ClientMapper, Client> {

  private final ClientMapper clientMapper;

  @Autowired
  public ClientDomain(ClientMapper clientMapper) {
    this.clientMapper = clientMapper;
    setMapper(clientMapper);
  }
}
