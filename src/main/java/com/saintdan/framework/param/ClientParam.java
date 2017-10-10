package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.domain.ClientDomain;
import com.saintdan.framework.enums.OperationType;
import io.swagger.annotations.ApiModelProperty;
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
@Data @EqualsAndHashCode(callSuper = false) @Builder @NoArgsConstructor @AllArgsConstructor
public class ClientParam extends BaseParam {

  private static final long serialVersionUID = 6065608866944007796L;

  @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
  private Long id;

  @ApiModelProperty(notes = "Name of service.")
  private String name;

  @NotNullField(value = OperationType.UPDATE, message = "publicKey cannot be null.")
  private String publicKey;

  private Integer accessTokenValiditySecondsAlias;

  private Integer refreshTokenValiditySecondsAlias;

  public ClientParam(Long id) {
    this.id = id;
  }
}
