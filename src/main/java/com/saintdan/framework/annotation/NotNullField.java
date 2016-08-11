package com.saintdan.framework.annotation;

import com.saintdan.framework.enums.OperationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Param field for signature.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullField {

  String message() default "{javax.validation.constraints.NotNull.message}";

  OperationType[] value() default OperationType.READ;
}
