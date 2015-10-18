package com.saintdan.framework.controller;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.SignHelper;
import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.param.RoleParam;
import com.saintdan.framework.service.RoleService;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role's controller.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourceURL.RESOURCES)
public class RoleController {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public ResultVO create(RoleParam param) {
        return null;
    }

    public ResultVO index() {
        return null;
    }

    public ResultVO show(@PathVariable String id) {
        return null;
    }

    public ResultVO update(RoleParam param) {
        return null;
    }

    public ResultVO delete(@PathVariable String id) {
        return null;
    }

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    private static final Log log = LogFactory.getLog(RoleController.class);

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SignHelper signHelper;
}
