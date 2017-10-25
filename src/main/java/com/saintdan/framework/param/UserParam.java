package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.annotation.SizeField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * Param bean for {@link com.saintdan.framework.domain.UserDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserParam extends BaseParam {

  private static final long serialVersionUID = -9153801716112918626L;

  @ApiModelProperty(hidden = true)
  private Long id;

  @ApiModelProperty(value = "username", required = true, notes = "usr must greater than or equal to 4 and less than or equal to 50.")
  @NotNullField(method = HttpMethod.POST, message = "usr cannot be null.")
  @SizeField(min = 4, max = 50, method = HttpMethod.POST, message = "usr must greater than or equal to 4 and less than or equal to 50.")
  private String usr; // username

  @ApiModelProperty(value = "password", required = true, notes = "pwd must greater than or equal to 4 and less than or equal to 16.")
  @NotNullField(method = HttpMethod.POST, message = "pwd cannot be null.")
  @SizeField(min = 4, max = 16, method = HttpMethod.POST, message = "pwd must greater than or equal to 4 and less than or equal to 16.")
  private String pwd; // password

  @ApiModelProperty(value = "user's name")
  private String name; // user's name

  private String description;

  @ApiModelProperty(value = "ids of roles", example = "1,2,3", notes = "separated by comma and no space.")
  private String roleIds; // role ids string

  public UserParam(String usr) {
    this.usr = usr;
  }
}
