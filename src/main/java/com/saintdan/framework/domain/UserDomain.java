package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.UserVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link User}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
@Service @Transactional(readOnly = true) public class UserDomain extends BaseDomain<User, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Transactional public UserVO create(UserParam param, User currentUser) throws Exception {
    return po2Vo(createReturnPo(param, currentUser));
  }

  @Transactional public User createReturnPo(UserParam param, User currentUser) throws Exception {
    return super.createByPO(param2Po(param, new User(), currentUser), currentUser);
  }

  public List<UserVO> getAll(Specification<User> specification, Sort sort) {
    return repository.findAll(specification, sort).stream().map(
        po -> {
          try {
            return po2Vo(po);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }).collect(Collectors.toList());
  }

  public Page getPage(Specification<User> specification, Pageable pageable) throws Exception {
    return getPage(specification, pageable, UserVO.class);
  }

  public User findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Transactional public UserVO update(UserParam param, User currentUser) throws Exception {
    return po2Vo(updateReturnPo(param, currentUser));
  }

  @Transactional public User updateReturnPo(UserParam param, User currentUser) throws Exception {
    User user = findById(param.getId());
    if (user == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    return super.updateByPO(transformer.param2PO(getClassT(), param, user, currentUser), currentUser);
  }

  @Transactional public void delete(UserParam param, User currentUser) throws Exception {
    super.delete(param.getId(), currentUser);
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private Transformer transformer;

  @Autowired private UserRepository repository;

  private User param2Po(UserParam param, User consult, User currentUser) throws Exception {
    return transformer.param2PO(User.class, param, consult, currentUser);
  }

  public UserVO po2Vo(User consult) throws Exception {
    return transformer.po2VO(UserVO.class, consult);
  }

}
