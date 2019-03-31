package com.saintdan.framework.starter.param;

import com.saintdan.framework.common.annotation.NotNullField;
import java.io.Serializable;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 15/02/2017
 * @since JDK1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginParam implements Serializable {

  private static final long serialVersionUID = 7458687134318597047L;

  @NotNullField(method = HttpMethod.POST, message = "usr cannot be null.")
  @Size(min = 4, max = 50, message = "usr must greater than or equal to 4 and less than or equal to 50.")
  private String usr;

  @NotNullField(method = HttpMethod.POST, message = "pwd cannot be null.")
  @Size(min = 4, max = 16, message = "pwd must greater than or equal to 4 and less than or equal to 16.")
  private String pwd;
}
