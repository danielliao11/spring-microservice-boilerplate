package com.saintdan.framework.starter.controller.management;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.controller.CRUDController;
import com.saintdan.framework.starter.domain.ResourceDomain;
import com.saintdan.framework.starter.po.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api for {@link Resource}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/3/31
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.RESOURCES)
public class ResourceController extends CRUDController<ResourceDomain, Resource, String> {

  private final ResourceDomain resourceDomain;

  @Autowired
  public ResourceController(ResourceDomain resourceDomain) {
    this.resourceDomain = resourceDomain;
    setDomain(resourceDomain);
  }
}
