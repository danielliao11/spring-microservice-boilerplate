package com.saintdan.framework.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saintdan.framework.annotation.SignField;
import com.saintdan.framework.constant.SignatureConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.tools.SignatureUtils;
import io.swagger.annotations.ApiModelProperty;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Base param.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(exclude = "currentUser")
@ToString(exclude = "currentUser")
public class BaseParam implements Serializable {

  private static final Set<String> baseFields = new HashSet<>();

  static {}

  private static final String EQUAL = "=";

  private static final long serialVersionUID = -103658650614029839L;

  @ApiModelProperty(hidden = true)
  private Integer pageNo;

  @ApiModelProperty(hidden = true)
  private Integer pageSize = 20;

  @ApiModelProperty(hidden = true)
  private String sortBy;

  @ApiModelProperty(hidden = true)
  private String sign;

  @ApiModelProperty(hidden = true)
  private UserDetails currentUser;

  public boolean isSignValid(String publicKey) throws CommonsException {
    String content = getSignContent();
    return SignatureUtils
        .rsaCheckContent(content, this.getSign(), publicKey, SignatureConstant.CHARSET_UTF8);
  }

  /**
   * Signature.
   *
   * @param privateKey Local private key.
   */
  public void sign(String privateKey) throws CommonsException {
    String content = getSignContent();//JsonConverter.convertToJSON(this).toString();
    this.sign = SignatureUtils.rsaSign(content, privateKey, SignatureConstant.CHARSET_UTF8);
  }

  /**
   * Get the signature content.
   *
   * @return signature content
   */
  @ApiModelProperty(hidden = true)
  public String getSignContent() throws CommonsException {
    StringBuffer buffer = new StringBuffer();
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
      PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
      Arrays.sort(pds, (o1, o2) -> (o1.getName().compareTo(o2.getName())));
      for (PropertyDescriptor pd : pds) {
        Method method = pd.getReadMethod();
        if (method == null) { // Ignore read-only field
          continue;
        }
        Field field = null;
        String itemName = pd.getName();
        try {
          field = baseFields.contains(itemName) ? BaseParam.class.getDeclaredField(itemName) :
              this.getClass().getDeclaredField(itemName);
        } catch (Exception ignored) {
          // Ignore
        }

        if (field == null || !field.isAnnotationPresent(SignField.class)) {
          continue; // Ignore field without ParamField annotation.
        }
        field.setAccessible(true);
        Object itemValue = field.get(this);
        if (itemValue == null) {
          continue;
        }
        buffer.append(itemName).append(EQUAL);
        if (itemValue.getClass().isAssignableFrom(List.class)) {
          List<?> list = (List<?>) itemValue;
          list.forEach(buffer::append);
        } else {
          buffer.append(itemValue);
        }
      }
      return buffer.toString();
    } catch (Exception e) {
      throw new CommonsException(ErrorType.UNKNOWN);
    }
  }
}
