package com.saintdan.framework.component;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.annotation.SizeField;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.GrantType;
import com.saintdan.framework.enums.OperationType;
import com.saintdan.framework.exception.IllegalTokenTypeException;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.ClientRepository;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.LoginUtils;
import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Validate helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/4/15
 * @since JDK1.8
 */
@Component public class ValidateHelper {

  /**
   * Validate client
   *
   * @param request request
   * @return 422
   * @throws IllegalTokenTypeException
   */
  public ResponseEntity validate(HttpServletRequest request) throws IllegalTokenTypeException {
    if (!clientRepository.findByClientIdAlias(LoginUtils.getClientId(request)).isPresent()) {
      return resultHelper.infoResp(ErrorType.SYS0007, ErrorType.SYS0007.description(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    return resultHelper.successResp(null, HttpStatus.OK);
  }

  /**
   * Validate client, param
   *
   * @param request   request
   * @param param     param
   * @param grantType {@link GrantType}
   * @return 422
   * @throws IllegalTokenTypeException
   */
  public ResponseEntity validate(HttpServletRequest request, Object param, GrantType grantType) throws IllegalTokenTypeException {
    if (!clientRepository.findByClientIdAlias(LoginUtils.getClientId(request)).isPresent()) {
      return resultHelper.infoResp(ErrorType.SYS0007, ErrorType.SYS0007.description(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    return validate(param, grantType);
  }

  /**
   * Validate current user and sign.
   *
   * @param param         param bean
   * @param currentUser   currentUser
   * @param logger        log
   * @param operationType {@link OperationType}
   * @return 422
   * @throws Exception
   */
  public ResponseEntity validate(Object param, User currentUser, Logger logger, OperationType operationType) {
    //check currentUser
    if (currentUser == null || currentUser.getId() == 0) {
      return resultHelper.infoResp(logger, ErrorType.SYS0003, ErrorType.SYS0003.description(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    return validate(param, operationType);
  }

  /**
   * Validate current user.
   *
   * @param currentUser currentUser
   * @param logger      log
   * @return 422
   */
  public ResponseEntity validate(User currentUser, Logger logger) {
    if (currentUser == null || currentUser.getId() == 0) {
      return resultHelper.infoResp(logger, ErrorType.SYS0003, ErrorType.SYS0003.description(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    return resultHelper.successResp(null, HttpStatus.OK);
  }

  /**
   * Bean properties null validation.
   *
   * @param param         Param bean
   * @param operationType {@link OperationType}
   * @return 422
   * @throws Exception
   */
  public ResponseEntity validate(Object param, OperationType operationType) {
    Field[] fields = param.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field == null || !field.isAnnotationPresent(NotNullField.class)) {
        continue; // Ignore field without ParamField annotation.
      }
      field.setAccessible(true);
      NotNullField notNullField = field.getAnnotation(NotNullField.class);
      try {
        if (ArrayUtils.contains(notNullField.value(), operationType) && (field.get(param) == null || StringUtils.isBlank(field.get(param).toString()))) {
          return resultHelper.infoResp(ErrorType.SYS0002, notNullField.message(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
      } catch (IllegalAccessException ignore) {}
      if (field.isAnnotationPresent(SizeField.class)) {
        SizeField size = field.getAnnotation(SizeField.class);
        try {
          if (ArrayUtils.contains(size.value(), operationType)
              && (field.get(param).toString().length() > size.max() || field.get(param).toString().length() < size.min())) {
            return resultHelper.infoResp(ErrorType.SYS0002, size.message(), HttpStatus.UNPROCESSABLE_ENTITY);
          }
        } catch (IllegalAccessException ignore) {}
      }
    }
    return ResponseEntity.ok().build();
  }

  /**
   * Bean properties null validation for auth.
   *
   * @param param         Param bean
   * @param grantType {@link GrantType}
   * @return 422
   * @throws Exception
   */
  public ResponseEntity validate(Object param, GrantType grantType) {
    Field[] fields = param.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field == null || !field.isAnnotationPresent(NotNullField.class)) {
        continue; // Ignore field without ParamField annotation.
      }
      field.setAccessible(true);
      NotNullField notNullField = field.getAnnotation(NotNullField.class);
      try {
        if (ArrayUtils.contains(notNullField.grant(), grantType) && (field.get(param) == null || StringUtils.isBlank(field.get(param).toString()))) {
          return resultHelper.infoResp(ErrorType.SYS0002, notNullField.message(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
      } catch (IllegalAccessException ignore) {}
      if (field.isAnnotationPresent(SizeField.class)) {
        SizeField size = field.getAnnotation(SizeField.class);
        try {
          if (ArrayUtils.contains(size.grant(), grantType)
              && (field.get(param).toString().length() > size.max() || field.get(param).toString().length() < size.min())) {
            return resultHelper.infoResp(ErrorType.SYS0002, size.message(), HttpStatus.UNPROCESSABLE_ENTITY);
          }
        } catch (IllegalAccessException ignore) {}
      }
    }
    return ResponseEntity.ok().build();
  }

  private final ResultHelper resultHelper;

  private final ClientRepository clientRepository;

  @Autowired
  public ValidateHelper(ResultHelper resultHelper, ClientRepository clientRepository) {
    Assert.defaultNotNull(resultHelper);
    Assert.defaultNotNull(clientRepository);
    this.resultHelper = resultHelper;
    this.clientRepository = clientRepository;
  }

}
