package com.saintdan.framework.starter.param;

import com.saintdan.framework.common.annotation.NotNullField;
import java.io.Serializable;
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
public class RefreshParam implements Serializable {

  private static final long serialVersionUID = -4157650685957393436L;

  @NotNullField(method = HttpMethod.PUT, message = "refresh token cannot be null.")
  private String refreshToken;
}
