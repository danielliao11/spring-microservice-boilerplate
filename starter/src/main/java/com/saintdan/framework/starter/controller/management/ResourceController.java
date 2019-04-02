package com.saintdan.framework.starter.controller.management;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.controller.CRUDController;
import com.saintdan.framework.common.tools.ResponseHelper;
import com.saintdan.framework.starter.domain.ResourceDomain;
import com.saintdan.framework.starter.domain.RoleAndResourceDomain;
import com.saintdan.framework.starter.po.Resource;
import com.saintdan.framework.starter.po.RoleAndResource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PutMapping("/{id}/roles")
  public ResponseEntity updateRoles(@PathVariable("id") String resourceId, @RequestBody List<RoleAndResource> roleAndResourceList) {
    roleAndResourceDomain.deleteByResourceId(resourceId);
    int result = roleAndResourceDomain.batchInsert(roleAndResourceList);
    if (result < 0) {
      return ResponseHelper.serverError("Update roles failed.");
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  private final ResourceDomain resourceDomain;
  private final RoleAndResourceDomain roleAndResourceDomain;

  @Autowired
  public ResourceController(ResourceDomain resourceDomain, RoleAndResourceDomain roleAndResourceDomain) {
    this.resourceDomain = resourceDomain;
    this.roleAndResourceDomain = roleAndResourceDomain;
    setDomain(resourceDomain);
  }
}