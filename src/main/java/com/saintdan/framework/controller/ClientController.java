package com.saintdan.framework.controller;

import com.saintdan.framework.annotation.CurrentUser;
import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.ValidateHelper;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ClientParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.service.ClientService;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            // Validate current user, param and sign.
            ResultVO resultVO = validateHelper.validate(currentUser, param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            // Return result and message.
            return clientService.create(param, currentUser);
        } catch (CommonsException e) {
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
            // Sign validate.
            ResultVO resultVO = validateHelper.validateSign(param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            return clientService.getAll();
        } catch (CommonsException e) {
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
            // Init page number.
            if (StringUtils.isBlank(pageNo)) {
                pageNo = "0";
            }
            ClientParam param = new ClientParam();
            // Sign validate.
            ResultVO resultVO = validateHelper.validateSign(param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            return clientService.getPage(new PageRequest(Integer.valueOf(pageNo), CommonsConstant.PAGE_SIZE), ResultVO.class);
        } catch (CommonsException e) {
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
            // Sign validate.
            ResultVO resultVO = validateHelper.validateSign(param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            return clientService.getById(ResultVO.class, param);
        } catch (CommonsException e) {
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
            ResultVO resultVO = validateHelper.validate(currentUser, param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            // Update client.
            return clientService.update(ResultVO.class, param, currentUser);
        } catch (CommonsException e) {
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
            // Sign validate.
            ResultVO resultVO = validateHelper.validateSign(param, sign, log);
            if (resultVO != null) {
                return resultVO;
            }
            // Delete client.
            clientService.delete(param, currentUser);
            final String ROLE = "client";
            return new ResultVO(ResultConstant.OK, OperationStatus.SUCCESS, String.format(ControllerConstant.DELETE, ROLE));
        } catch (CommonsException e) {
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

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private ValidateHelper validateHelper;

    @Autowired
    private ClientService clientService;
}
