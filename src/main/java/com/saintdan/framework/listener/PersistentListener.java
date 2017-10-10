package com.saintdan.framework.listener;

import com.saintdan.framework.constant.CommonsConstant;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import javax.persistence.PrePersist;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;

/**
 * Persistent created time.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/10/2017
 * @since JDK1.8
 */
public class PersistentListener {

  @PrePersist
  public void onCreate(Object object) {
    final String ID = "id";
    final String CREATED_AT = "createdAt";
    final String LAST_MODIFIED_AT = "lastModifiedAt";
    BeanUtilsBean beanUtilsBean = BeanUtilsBean2.getInstance();
    try {
      if (Objects.equals(beanUtilsBean.getProperty(object, ID), CommonsConstant.ZERO)) {
        beanUtilsBean.setProperty(object, CREATED_AT, System.currentTimeMillis());
        beanUtilsBean.setProperty(object, LAST_MODIFIED_AT, System.currentTimeMillis());
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {}
  }
}
