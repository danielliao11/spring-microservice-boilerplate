package com.saintdan.framework.starter.domain;

import com.saintdan.framework.common.domain.BaseDomain;
import com.saintdan.framework.starter.mapper.ResourceMapper;
import com.saintdan.framework.starter.po.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/2/26
 * @since JDK1.8
 */
@Service
public class ResourceDomain extends BaseDomain<ResourceMapper, Resource> {

  private final ResourceMapper resourceMapper;

  @Autowired
  public ResourceDomain(ResourceMapper resourceMapper) {
    this.resourceMapper = resourceMapper;
    setMapper(resourceMapper);
  }
}
