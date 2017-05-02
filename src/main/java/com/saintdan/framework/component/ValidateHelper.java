package com.saintdan.framework.component;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.annotation.SizeField;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.GrantType;
import com.saintdan.framework.enums.OperationType;
import com.saintdan.framework.param.BaseParam;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.Assert;
import java.lang.reflect.Field;
import org.apache.commons.lang3.ArrayUtils;
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

  private final ResultHelper resultHelper;

  @Autowired public ValidateHelper(ResultHelper resultHelper) {
    Assert.defaultNotNull(resultHelper);
    this.resultHelper = resultHelper;
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
  public ResponseEntity validate(BaseParam param, User currentUser, Logger logger, OperationType operationType) throws Exception {
    //check currentUser
    if (currentUser == null || currentUser.getId() == null) {
      return resultHelper.infoResp(logger, ErrorType.SYS0003, ErrorType.SYS0003.description(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    return validate(param, operationType);
  }

  /**
   * Bean properties null validation.
   *
   * @param param         Param bean
   * @param operationType {@link OperationType}
   * @return 422
   * @throws Exception
   */
  public ResponseEntity validate(BaseParam param, OperationType operationType) throws Exception {
    Field[] fields = param.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field == null || !field.isAnnotationPresent(NotNullField.class) || !field.isAnnotationPresent(SizeField.class)) {
        continue; // Ignore field without ParamField annotation.
      }
      field.setAccessible(true);
      NotNullField notNullField = field.getAnnotation(NotNullField.class);
      if (ArrayUtils.contains(notNullField.value(), operationType) && field.get(param) == null) {
        return resultHelper.infoResp(ErrorType.SYS0002, notNullField.message(), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      if (field.isAnnotationPresent(SizeField.class)) {
        SizeField size = field.getAnnotation(SizeField.class);
        if (ArrayUtils.contains(size.value(), operationType)
            && (field.get(param).toString().length() > size.max() || field.get(param).toString().length() < size.min())) {
          return resultHelper.infoResp(ErrorType.SYS0002, size.message(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
      }
    }
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * Bean properties null validation for auth.
   *
   * @param param         Param bean
   * @param grantType {@link GrantType}
   * @return 422
   * @throws Exception
   */
  public ResponseEntity validate(BaseParam param, GrantType grantType) throws Exception {
    Field[] fields = param.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field == null || !field.isAnnotationPresent(NotNullField.class)) {
        continue; // Ignore field without ParamField annotation.
      }
      field.setAccessible(true);
      NotNullField notNullField = field.getAnnotation(NotNullField.class);
      if (ArrayUtils.contains(notNullField.grant(), grantType) && field.get(param) == null) {
        return resultHelper.infoResp(ErrorType.SYS0002, notNullField.message(), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      if (field.isAnnotationPresent(SizeField.class)) {
        SizeField size = field.getAnnotation(SizeField.class);
        if (ArrayUtils.contains(size.grant(), grantType)
            && (field.get(param).toString().length() > size.max() || field.get(param).toString().length() < size.min())) {
          return resultHelper.infoResp(ErrorType.SYS0002, size.message(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
      }
    }
    return new ResponseEntity(HttpStatus.OK);
  }

}
