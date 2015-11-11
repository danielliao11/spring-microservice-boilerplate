package com.saintdan.framework.component;

import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.param.BaseParam;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.service.ClientService;
import com.saintdan.framework.tools.SpringSecurityUtils;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
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
@Component
public class ValidateHelper {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Validate current user, param and sign.
     *
     * @param result            bind result
     * @param currentUser       current user
     * @param param             param
     * @param sign              sign
     * @param log               log
     * @return                  result vo
     * @throws Exception
     */
    public ResultVO validate(BindingResult result, User currentUser, BaseParam param, String sign, Log log) throws Exception {
        if (result.hasErrors()) {
            return resultHelper.infoResp(ErrorType.SYS0002, result.toString());
        }
        return validate(currentUser, param, sign, log);
    }

    /**
     * Validate current user and sign.
     *
     * @param currentUser currentUser
     * @param param       param
     * @param sign        signature
     * @param log         log
     * @return result VO
     * @throws Exception
     */
    public ResultVO validate(User currentUser, BaseParam param, String sign, Log log)throws Exception {
        //check currentUser
        if (currentUser == null || currentUser.getId() == null) {
            return resultHelper.infoResp(log, ErrorType.SYS0003);
        }
        return validate(param, sign, log);
    }

    /**
     * Validate sign.
     *
     * @param param param
     * @param sign  signature
     * @param log   log
     * @return result VO
     * @throws Exception
     */

    public ResultVO validate(BaseParam param, String sign, Log log) throws Exception {
        // Prepare to validate signature.
        param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
        // Get current clientId
        String clientId = SpringSecurityUtils.getCurrentClientId();
        // Sign verification.
        if (!signHelper.signCheck(getPublicKeyByClientId(clientId), param, sign)) {
            // Return rsa signature failed information and log the exception.
            return resultHelper.infoResp(log, ErrorType.SYS0004);
        } else
            return null;
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private SignHelper signHelper;

    @Autowired
    private ClientService clientService;

    /**
     * Get public key by client id.
     *
     * @param clientId      client id
     * @return              public key
     * @exception Exception
     */
    private String getPublicKeyByClientId(String clientId) throws Exception {
        return clientService.getClientByClientId(new ClientParam(clientId)).getPublicKey();
    }

}
