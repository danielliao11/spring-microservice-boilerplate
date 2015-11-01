package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.ClientException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.service.ClientService;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Client's controller
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
@PropertySource("classpath:api.properties")
@RestController
@RequestMapping(ResourceURL.RESOURCES)
public class ClientController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new client.
     *
     * @param param     client's param
     * @return          client's result
     */
    @RequestMapping(value = ResourceURL.CLIENTS + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO create(@CurrentUser User currentUser, ClientParam param, @PathVariable String sign) {
        try {
            // Get incorrect params.
            String validateContent = param.getIncorrectParams();
            if (!StringUtils.isBlank(validateContent)) {
                // If validate failed, return error message.
                return new ResultVO(ErrorType.SYS0002.description(), OperationStatus.FAILURE,
                        String.format(ControllerConstant.PARAM_BLANK, validateContent));
            }
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Return result and message.
            return clientService.create(param, currentUser);
        } catch (ClientException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show all clients' VO.
     *
     * @return          clients' result
     */
    @RequestMapping(value = ResourceURL.CLIENTS + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO index(@PathVariable String sign) {
        try {
            ClientParam param = new ClientParam();
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            return clientService.getAllClients();
        } catch (ClientException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show clients' page.
     *
     * @param pageNo        page number
     * @return              clients' page
     */
    @RequestMapping(value = ResourceURL.CLIENTS + "/pageNo={pageNo}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO page(@PathVariable String pageNo, @PathVariable String sign) {
        try {
            ClientParam param = new ClientParam();
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Init page number.
            if (StringUtils.isBlank(pageNo)) {
                pageNo = "0";
            }
            return clientService.getPage(new PageRequest(Integer.valueOf(pageNo), CommonsConstant.PAGE_SIZE));
        } catch (ClientException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Show client by ID.
     *
     * @param id        client's id
     * @return          client's result
     */
    @RequestMapping(value = ResourceURL.CLIENTS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.GET)
    public ResultVO show(@PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            ClientParam param = new ClientParam(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            return clientService.getClientById(param);
        } catch (ClientException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Update client.
     *
     * @param id        client's id
     * @param param     client's params
     * @return          client's result
     */
    @RequestMapping(value = ResourceURL.CLIENTS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.POST)
    public ResultVO update(@CurrentUser User currentUser, @PathVariable String id, @PathVariable String sign, ClientParam param) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            // Get incorrect params.
            String validateContent = param.getIncorrectParams();
            if (!StringUtils.isBlank(validateContent)) {
                // If validate failed, return error message.
                return new ResultVO(ErrorType.SYS0002.description(), OperationStatus.FAILURE,
                        String.format(ControllerConstant.PARAM_BLANK, validateContent));
            }
            // Set client's ID.
            param.setId(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Update client.
            return clientService.update(param, currentUser);
        } catch (ClientException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    /**
     * Delete client.
     *
     * @param id        client's id
     * @return          client's result
     */
    @RequestMapping(value = ResourceURL.CLIENTS + "/{id}" + ResourceURL.SIGN, method = RequestMethod.DELETE)
    public ResultVO delete(@CurrentUser User currentUser, @PathVariable String id, @PathVariable String sign) {
        try {
            if (StringUtils.isBlank(id)) {
                return resultHelper.infoResp(ErrorType.SYS0002, String.format(ControllerConstant.PARAM_BLANK, ControllerConstant.ID_PARAM));
            }
            ClientParam param = new ClientParam(Long.valueOf(id));
            // Prepare to validate signature.
            param.setSign(new String(Base64.decodeBase64(sign.getBytes())));
            // Sign verification.
            if (!signHelper.signCheck(PUBLIC_KEY, param, sign)) {
                // Return rsa signature failed information and log the exception.
                return resultHelper.infoResp(log, ErrorType.SGN0021);
            }
            // Delete client.
            clientService.delete(param, currentUser);
            final String ROLE = "client";
            return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, ROLE));
        } catch (ClientException e) {
            // Return error information and log the exception.
            return resultHelper.infoResp(log, e.getErrorType());
        } catch (Exception e) {
            // Return unknown error and log the exception.
            return resultHelper.errorResp(log, e, ErrorType.UNKNOWN, e.getMessage());
        }
    }

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    private static final Log log = LogFactory.getLog(ClientController.class);

    @Value("${opposite.end1.publicKey}")
    private String PUBLIC_KEY;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SignHelper signHelper;
}
