package com.saintdan.framework.component;

import com.saintdan.framework.domain.ClientDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.param.BaseParam;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.SpringSecurityUtils;
import com.saintdan.framework.vo.ResultVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * Validate helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/4/15
 * @since JDK1.8
 */
@Component public class ValidateHelper {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Validate current user, param and sign.
   *
   * @param result      bind result
   * @param currentUser current user
   * @param logger      log
   * @return result vo
   * @throws Exception
   */
  public ResultVO validateWithOutSignCheck(BindingResult result, User currentUser, Logger logger) throws Exception {
    if (result.hasErrors()) {
      return resultHelper.infoResp(ErrorType.SYS0002, result.toString());
    }
    return validateWithOutSignCheck(currentUser, logger);
  }

  /**
   * Validate current user and sign.
   *
   * @param currentUser currentUser
   * @param logger      log
   * @return result VO
   * @throws Exception
   */
  public ResultVO validateWithOutSignCheck(User currentUser, Logger logger) throws Exception {
    //check currentUser
    if (currentUser == null || currentUser.getId() == null) {
      return resultHelper.infoResp(logger, ErrorType.SYS0003, ErrorType.SYS0003.description());
    }
    return null;
  }

  /**
   * Validate current user, param and sign.
   *
   * @param result      bind result
   * @param currentUser current user
   * @param param       param
   * @param logger      log
   * @return result vo
   * @throws Exception
   */
  public ResultVO validateWithSignCheck(BindingResult result, User currentUser, BaseParam param, Logger logger) throws Exception {
    if (result.hasErrors()) {
      return resultHelper.infoResp(ErrorType.SYS0002, result.toString());
    }
    return validateWithSignCheck(currentUser, param, logger);
  }

  /**
   * Validate current user and sign.
   *
   * @param currentUser currentUser
   * @param param       param
   * @param logger      log
   * @return result VO
   * @throws Exception
   */
  public ResultVO validateWithSignCheck(User currentUser, BaseParam param, Logger logger) throws Exception {
    //check currentUser
    if (currentUser == null || currentUser.getId() == null) {
      return resultHelper.infoResp(logger, ErrorType.SYS0003, ErrorType.SYS0003.description());
    }
    return validateWithSignCheck(param, logger);
  }

  /**
   * Validate sign.
   *
   * @param param  param
   * @param logger log
   * @return result VO
   * @throws Exception
   */

  public ResultVO validateWithSignCheck(BaseParam param, Logger logger) throws Exception {
    // Get current clientId
    String clientId = SpringSecurityUtils.getCurrentClientId();
    // Sign verification.
    if (!signHelper.signCheck(getPublicKeyByClientId(clientId), param)) {
      // Return rsa signature failed information and log the exception.
      return resultHelper.infoResp(logger, ErrorType.SYS0004, ErrorType.SYS0004.description());
    } else
      return null;
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private ResultHelper resultHelper;

  @Autowired private SignHelper signHelper;

  @Autowired private ClientDomain clientDomain;

  /**
   * Get public key by client id.
   *
   * @param clientId client id
   * @return public key
   * @throws Exception
   */
  private String getPublicKeyByClientId(String clientId) throws Exception {
    return clientDomain.getClientByClientId(new ClientParam(clientId)).getPublicKey();
  }

}
