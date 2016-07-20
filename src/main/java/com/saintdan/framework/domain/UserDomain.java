package com.saintdan.framework.domain;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ResourceConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.UserParam;
import com.saintdan.framework.po.Role;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.UserVO;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link User}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
public class UserDomain extends BaseDomain<User, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create new {@link User}.
   *
   * @param currentUser current user
   * @param param       {@link UserParam}
   * @return {@link UserVO}
   * @throws CommonsException {@link ErrorType#SYS0111} user already existing, usr taken.
   */
  @Transactional public UserVO create(UserParam param, User currentUser) throws Exception {
    if (userRepository.findByUsr(param.getUsr()).isPresent()) {
      // Throw user already exists error, usr taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, ResourceConstant.USERS, USR));
    }
    return super.createByPO(UserVO.class, userParam2PO(param, new User(), currentUser), currentUser);
  }

  /**
   * Get {@link UserVO} by user's usr.
   *
   * @param param {@link UserParam}
   * @return {@link UserVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any user by usr param.
   */
  public UserVO getUserByUsr(UserParam param) throws Exception {
    return transformer.po2VO(UserVO.class, findByUsr(param.getUsr()));
  }

  public User findByUsr(String usr) throws Exception {
    return userRepository.findByUsr(usr).orElseThrow(
        // Throw cannot find any user by this usr param.
        () -> new CommonsException(ErrorType.SYS0122, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, ResourceConstant.USERS, USR)));
  }

  /**
   * Update {@link User}.
   *
   * @param param {@link UserParam}
   * @return {@link UserVO}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any user by id param.
   */
  @Transactional public UserVO update(UserParam param, User currentUser) throws Exception {
    findById(param.getId());
    return super.updateByPO(UserVO.class, userParam2PO(param, new User(), currentUser), currentUser);
  }

  /**
   * Update password of {@link User}
   *
   * @param currentUser current user
   * @param param       {@link UserParam}
   * @throws CommonsException {@link ErrorType#SYS0122} user's pwd update failed.
   */
  @Transactional public void updatePwd(UserParam param, User currentUser) throws Exception {
    findById(param.getId());
    final String USER = "User";
    logHelper.logUsersOperations(LogType.UPDATE, USER, currentUser);
    userRepository.updatePwdFor(param.getPwd(), param.getId());
  }

  /**
   * Delete {@link User}.
   *
   * @param currentUser current user
   * @param id          {@link User#id}
   * @throws CommonsException {@link ErrorType#SYS0122} Cannot find any user by id param.
   */
  @Transactional public void delete(Long id, User currentUser) throws Exception {
    User user = findById(id);
    // Log delete operation.
    logHelper.logUsersOperations(LogType.DELETE, ResourceConstant.USERS, currentUser);
    // Change valid flag to invalid.
    userRepository.updateValidFlagFor(ValidFlag.INVALID, user.getId());
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private RoleDomain roleDomain;

  @Autowired private UserRepository userRepository;

  @Autowired private CustomPasswordEncoder passwordEncoder;

  @Autowired private Transformer transformer;

  @Autowired private LogHelper logHelper;

  @Autowired public UserDomain(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private static final String USR = "usr";

  /**
   * Transform {@link UserParam} to {@link User}.
   *
   * @param param       {@link UserParam}
   * @param user        {@link User}
   * @param currentUser currentUser
   * @return {@link User}
   */
  private User userParam2PO(UserParam param, User user, User currentUser) throws Exception {
    transformer.param2PO(getClassT(), param, user, currentUser);
    if (!StringUtils.isBlank(param.getRoleIds())) {
      List<Role> roles = roleDomain.getAllByIds(transformer.idsStr2List(param.getRoleIds()));
      user.setRoles(transformer.list2Set(roles));
    }
    if (!StringUtils.isBlank(param.getPwd())) {
      user.setPwd(passwordEncoder.encode(param.getPwd()));
    }
    return user;
  }

  private User findById(Long id) throws Exception {
    return userRepository.findById(id).orElseThrow(
        // Throw cannot find any user by this id param.
        () -> new CommonsException(ErrorType.SYS0122, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, ResourceConstant.USERS, CommonsConstant.ID)));
  }

}
