package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.LogException;
import com.saintdan.framework.param.LogParam;
import com.saintdan.framework.po.Log;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.LogRepository;
import com.saintdan.framework.service.LogService;
import com.saintdan.framework.vo.LogVO;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the
 * {@link LogService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
@Service
public class LogServiceImpl implements LogService {

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
    public LogVO create(LogParam param, User currentUser) {
        return logPO2VO(logRepository.save(logParam2PO(param, currentUser)),
                String.format(ControllerConstant.CREATE, LOG));
    }

    /**
     * Show all logs.
     *
     * @return          logs' VO
     * @throws LogException      LOG0011 Cannot find any log, no log exists.
     */
    @Override
    public ObjectsVO getAllLogs() throws LogException {
        List<Log> logs = (List<Log>) logRepository.findAll();
        if (logs.isEmpty()) {
            // Throw no log exist exception.
            throw new LogException(ErrorType.LOG0011);
        }
        return logsPO2VO(logs, String.format(ControllerConstant.INDEX, LOG));
    }

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              logs' page VO
     * @throws LogException      LOG0011 Cannot find any log, no log exists.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws LogException {
        Page<Log> logPage = logRepository.findAll(pageable);
        if (logPage.getContent().isEmpty()) {
            // Throw no log exist exception.
            throw new LogException(ErrorType.LOG0011);
        }
        return transformer.poPage2VO(
                poList2VOList(logPage.getContent()),
                pageable, logPage.getTotalElements(),
                String.format(ControllerConstant.INDEX, LOG));
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private Transformer transformer;

    private final static String LOG = "log";

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

    /**
     * Transform log's PO to VO.
     *
     * @param log           log's PO
     * @param msg           return message
     * @return              log's VO
     */
    private LogVO logPO2VO(Log log, String msg) {
        LogVO vo = new LogVO();
        BeanUtils.copyProperties(log, vo);
        if (StringUtils.isBlank(msg)) {
            return vo;
        }
        vo.setMessage(msg);
        // Return success result.
        return (LogVO) resultHelper.sucessResp(vo);
    }

    /**
     * Transform logs' PO to logs VO
     *
     * @param logs          logs' PO
     * @param msg           return message
     * @return              logs' VO
     */
    private ObjectsVO logsPO2VO(Iterable<Log> logs, String msg) {
        List objList = poList2VOList(logs);
        ObjectsVO vos = transformer.voList2ObjectsVO(objList, msg);
        return (ObjectsVO) resultHelper.sucessResp(vos);
    }

    /**
     * Transform log's PO list to VO list.
     *
     * @param logs          log's PO list
     * @return              log's VO list
     */
    private List<LogVO> poList2VOList(Iterable<Log> logs) {
        List<LogVO> logVOList = new ArrayList<>();
        for (Log log : logs) {
            LogVO vo = logPO2VO(log, "");
            logVOList.add(vo);
        }
        return logVOList;
    }
}
