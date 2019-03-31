package com.saintdan.framework.starter.controller.management;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.controller.CRUDController;
import com.saintdan.framework.starter.domain.ClientDomain;
import com.saintdan.framework.starter.po.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api for {@link Client}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/3/31
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.CLIENTS)
public class ClientController extends CRUDController<ClientDomain, Client, String> {

  private final ClientDomain clientDomain;

  @Autowired
  public ClientController(ClientDomain clientDomain) {
    this.clientDomain = clientDomain;
    setDomain(clientDomain);
  }
}
