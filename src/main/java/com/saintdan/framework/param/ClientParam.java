package com.saintdan.framework.param;

import com.saintdan.framework.domain.ClientDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
  private Long id;
  private String name;
  private String publicKey;
  private String scope;
  private String grantType;
  private Integer accessTokenValiditySeconds;
  private Integer refreshTokenValiditySeconds;
}
