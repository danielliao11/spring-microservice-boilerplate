package com.saintdan.framework.service;

import com.saintdan.framework.param.LoginParam;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 * Service for register.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 23/01/2017
 * @since JDK1.8
 */
public interface LoginService {

  ResponseEntity login(LoginParam param, HttpServletRequest request) throws Exception;

  ResponseEntity refresh(LoginParam param, HttpServletRequest request) throws Exception;
}
