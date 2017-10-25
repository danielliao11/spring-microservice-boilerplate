package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.domain.ClientDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * Param bean for {@link ClientDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientParam extends BaseParam {

  private static final long serialVersionUID = 6065608866944007796L;

  @ApiModelProperty(hidden = true)
  private Long id;

  @NotNullField(method = HttpMethod.POST, message = "name cannot be null.")
  private String name;

  @NotNullField(method = HttpMethod.POST, message = "publicKey cannot be null.")
  private String publicKey;

  @NotNullField(method = HttpMethod.POST, message = "scope cannot be null.")
  private String scope;

  @NotNullField(method = HttpMethod.POST, message = "grantType cannot be null.")
  private String grantType;
  private Integer accessTokenValiditySeconds;
  private Integer refreshTokenValiditySeconds;
}
