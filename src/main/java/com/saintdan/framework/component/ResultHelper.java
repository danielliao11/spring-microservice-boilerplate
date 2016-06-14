package com.saintdan.framework.component;

import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.OperationStatus;
import com.saintdan.framework.tools.LogUtils;
import com.saintdan.framework.vo.ResultVO;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Log debug message and return result.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
@Component
public class ResultHelper {

  /**
   * Return success result.
   *
   * @param object some vo.
   * @return result vo.
   */
  public ResultVO successResp(Object object) {
    ResultVO vo = new ResultVO(ResultConstant.OK);
    vo.setOperationStatus(OperationStatus.SUCCESS);
    if (vo.getMessage() == null) {
      vo.setMessage(ResultConstant.SUCCESS);
    }
    vo.setData(object);
    return vo;
  }

  /**
   * Return error information.
   *
   * @param errorType error type
   * @return result vo
   */
  public ResultVO infoResp(ErrorType errorType) {
    return infoResp(errorType, errorType.description());
  }

  /**
   * Return error information.
   *
   * @param errorType error type
   * @param msg       error message
   * @return result vo
   */
  public ResultVO infoResp(ErrorType errorType, String msg) {
    return new ResultVO(
        errorType.name(),
        OperationStatus.FAILURE,
        msg
    );
  }

  /**
   * Return error information.
   *
   * @param logger    Log
   * @param errorType error type
   * @return result vo
   */
  public ResultVO infoResp(Logger logger, ErrorType errorType) {
    LogUtils.trackInfo(logger, errorType.description());
    return new ResultVO(
        errorType.name(),
        OperationStatus.FAILURE,
        errorType.description()
    );
  }

  /**
   * Return error information,
   * and log it to error.
   *
   * @param logger    Log
   * @param errorType error type
   * @param e         e
   * @return result vo
   */
  public ResultVO errorResp(Logger logger, Throwable e, ErrorType errorType) {
    return errorResp(logger, e, errorType, errorType.description());
  }

  /**
   * Return error information,
   * and log it to error.
   *
   * @param logger    Log
   * @param errorType error type
   * @param e         e
   * @param msg       error message
   * @return result vo
   */
  public ResultVO errorResp(Logger logger, Throwable e, ErrorType errorType, String msg) {
    LogUtils.traceError(logger, e, errorType.description());
    return new ResultVO(
        errorType.name(),
        OperationStatus.FAILURE,
        msg
    );
  }

}
