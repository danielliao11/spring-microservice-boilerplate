package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ResourceParam;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.CustomRepository;
import com.saintdan.framework.repo.ResourceRepository;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ResourceVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link Resource}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
public class ResourceDomain extends BaseDomain<Resource, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Transactional public ResourceVO create(ResourceParam param, User currentUser) throws Exception {
    nameExists(param.getName());
    return super.createByPO(ResourceVO.class, resourceParam2PO(param, new Resource(), currentUser));
  }

  public List<ResourceVO> all() {
    return resourceRepository.findAll().stream()
        .map(resource -> {
          try {
            return transformer.po2VO(ResourceVO.class, resource);
          } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }).collect(Collectors.toList());
  }

  @Transactional public ResourceVO update(ResourceParam param, User currentUser) throws Exception {
    Resource resource = findById(param.getId());
    if (!param.getName().equals(resource.getName())) {
      nameExists(param.getName());
    }
    return super.updateByPO(ResourceVO.class, resourceParam2PO(param, resource, currentUser));
  }

  public Resource findById(Long id) {
    return resourceRepository.findById(id).orElse(null);
  }

  @Transactional @Override public void deepDelete(Long id) throws Exception {
    Resource resource = findById(id);
    if (resource == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName().toLowerCase(),
              CommonsConstant.ID));
    }
    resourceRepository.delete(resource);
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  private final ResourceRepository resourceRepository;

  public ResourceDomain(CustomRepository<Resource, Long> repository, Transformer transformer,
      ResourceRepository resourceRepository) {
    super(repository, transformer);
    Assert.defaultNotNull(resourceRepository);
    this.resourceRepository = resourceRepository;
  }

  private Resource resourceParam2PO(ResourceParam param, Resource resource, User currentUser)
      throws Exception {
    return transformer.param2PO(getClassT(), param, resource, currentUser);
  }

  private void nameExists(String name) throws Exception {
    if (resourceRepository.findByNameAndValidFlag(name, ValidFlag.VALID).isPresent()) {
      // Throw group already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName().toLowerCase(),
              CommonsConstant.NAME));
    }
  }
}
