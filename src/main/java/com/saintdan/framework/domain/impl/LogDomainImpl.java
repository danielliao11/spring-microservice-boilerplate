package com.saintdan.framework.domain.impl;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.LogParam;
import com.saintdan.framework.po.Log;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.LogRepository;
import com.saintdan.framework.domain.LogDomain;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.LogVO;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implements the
 * {@link LogDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
@Service
@Transactional
public class LogDomainImpl implements LogDomain {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new log.
     *
     * @param currentUser   current user
     * @param param         log's param
     * @return              log's VO
     */
    @Override
    public LogVO create(LogParam param, User currentUser) throws Exception {
        return transformer.po2VO(LogVO.class, logRepository.save(logParam2PO(param, currentUser)),
                String.format(ControllerConstant.CREATE, LOG));
    }

    /**
     * Show all logs.
     *
     * @return          logs' VO
     * @throws CommonsException        SYS0120 No group exists.
     */
    @Override
    public ObjectsVO getAllLogs() throws Exception {
        List<Log> logs = (List<Log>) logRepository.findAll();
        if (logs.isEmpty()) {
            // Throw no log exist exception.
            throw new CommonsException(ErrorType.SYS0120, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, LOG, LOG));
        }
        return transformer.pos2VO(ObjectsVO.class, logs, String.format(ControllerConstant.INDEX, LOG));
    }

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              logs' page VO
     * @throws CommonsException        SYS0120 No group exists.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws Exception {
        Page<Log> logPage = logRepository.findAll(pageable);
        if (logPage.getContent().isEmpty()) {
            // Throw no log exist exception.
            throw new CommonsException(ErrorType.SYS0120, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, LOG, LOG));
        }
        return transformer.poPage2VO(transformer.poList2VOList(LogVO.class, logPage.getContent()), pageable, logPage.getTotalElements(),
                String.format(ControllerConstant.INDEX, LOG));
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private Transformer transformer;

    private final static String LOG = "log";


    @Autowired
    private LogRepository logRepository;

    /**
     * Transform log's param to PO.
     *
     * @param param         log's param
     * @return              log's PO
     */
    private Log logParam2PO(LogParam param, User currentUser) {
        Log log = new Log();
        BeanUtils.copyProperties(param, log);
        log.setUserId(currentUser.getId());
        log.setUsername(currentUser.getUsr());
        return log;
    }
}
