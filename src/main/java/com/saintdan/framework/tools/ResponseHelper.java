package com.saintdan.framework.tools;

import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.vo.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/4
 * @since JDK1.8
 */
public class ResponseHelper {

  public static ResponseEntity unknownError() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorVO(ErrorType.UNKNOWN_ERROR.code(), ErrorType.UNKNOWN_ERROR.msg()));
  }

  public static ResponseEntity clientError(int code, String msg) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(new ErrorVO(code, msg));
  }
}
