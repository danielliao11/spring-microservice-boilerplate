package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Logs' VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public class LogsVO implements Serializable {

    private static final long serialVersionUID = -2007168234948399235L;

    private List<LogVO> logVOList;

    public List<LogVO> getLogVOList() {
        return logVOList;
    }

    public void setLogVOList(List<LogVO> logVOList) {
        this.logVOList = logVOList;
    }
}
