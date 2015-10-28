package com.saintdan.framework.service;

import com.saintdan.framework.exception.LogException;
import com.saintdan.framework.param.LogParam;
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
     * @param param     log's param
     * @return          log's VO
     */
    LogVO create(LogParam param);

    /**
     * Show all logs.
     *
     * @return          logs' VO
     * @throws LogException      LOG0011 Cannot find any log, no log exists.
     */
    ObjectsVO getAllLogs() throws LogException;

    /**
     * Show users' page VO.
     *
     * @param pageable      page
     * @return              logs' page VO
     * @throws LogException      LOG0011 Cannot find any log, no log exists.
     */
    PageVO getPage(Pageable pageable) throws LogException;

}
