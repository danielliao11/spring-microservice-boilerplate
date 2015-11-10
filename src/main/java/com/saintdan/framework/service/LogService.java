package com.saintdan.framework.service;

import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.LogParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.LogVO;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.springframework.data.domain.Pageable;

/**
 * Log's service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public interface LogService {

    /**
     * Create new log.
     *
     * @param currentUser   current user
     * @param param         log's param
     * @return              log's VO
     */
    LogVO create(LogParam param, User currentUser) throws Exception;

    /**
     * Show all logs.
     *
     * @return          logs' VO
     * @throws CommonsException        SYS0120 No group exists.
     */
    ObjectsVO getAllLogs() throws Exception;

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              logs' page VO
     * @throws CommonsException        SYS0120 No group exists.
     */
    PageVO getPage(Pageable pageable) throws Exception;

}
