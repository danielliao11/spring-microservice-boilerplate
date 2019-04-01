package com.saintdan.framework.common.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saintdan.framework.common.annotation.SignField;
import com.saintdan.framework.common.constant.SignatureConstant;
import com.saintdan.framework.common.exception.SignFailedException;
import com.saintdan.framework.common.tools.SignatureUtils;
import io.swagger.annotations.ApiModelProperty;
import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Transient;
import lombok.Data;

/**
 * Base param.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
@Data
public class BaseParam implements Serializable {

  private static final Map<String, Object> baseFields = new HashMap<>();

  static {}

  private static final String EQUAL = "=";

  private static final long serialVersionUID = -103658650614029839L;

  @ApiModelProperty(hidden = true)
  @Transient
  @JsonIgnore
  private Integer pageNo = 1;

  @ApiModelProperty(hidden = true)
  @Transient
  @JsonIgnore
  private Integer pageSize = 20;

  @ApiModelProperty(hidden = true)
  @Transient
  @JsonIgnore
  private String sortBy;

  @ApiModelProperty(hidden = true)
  @Transient
  @JsonIgnore
  private String sign;

  @JsonIgnore
  public Map<String, Object> getBaseFields() {
    baseFields.put("pageNo", pageNo);
    baseFields.put("pageSize", pageSize);
    baseFields.put("sortBy", sortBy);
    baseFields.put("sign", sign);
    return baseFields;
  }

  public boolean isSignValid(String publicKey) throws SignFailedException {
    String content = getSignContent();
    return SignatureUtils.rsaCheckContent(content, this.getSign(), publicKey, SignatureConstant.CHARSET_UTF8);
  }

  /**
   * Signature.
   *
   * @param privateKey Local private key.
   */
  public void sign(String privateKey) throws SignFailedException {
    String content = getSignContent(); //JsonConverter.convertToJSON(this).toString();
    this.sign = SignatureUtils.rsaSign(content, privateKey, SignatureConstant.CHARSET_UTF8);
  }

  /**
   * Get the signature content.
   *
   * @return signature content
   */
  @ApiModelProperty(hidden = true)
  @JsonIgnore
  public String getSignContent() throws SignFailedException {
    StringBuffer buffer = new StringBuffer();
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
      PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
      Arrays.sort(pds, Comparator.comparing(FeatureDescriptor::getName));
      for (PropertyDescriptor pd : pds) {
        Method method = pd.getReadMethod();
        if (method == null) { // Ignore read-only field
          continue;
        }
        Field field = null;
        String itemName = pd.getName();
        try {
          field = getBaseFields().containsKey(itemName) ? BaseParam.class.getDeclaredField(itemName) : this.getClass().getDeclaredField(itemName);
        } catch (Exception ignored) {}
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
      throw new SignFailedException();
    }
  }
}
