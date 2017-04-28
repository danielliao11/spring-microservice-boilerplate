package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.annotation.SignField;
import com.saintdan.framework.annotation.SizeField;
import com.saintdan.framework.enums.OperationType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Size;

/**
 * Param bean for {@link com.saintdan.framework.domain.UserDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
public class UserParam extends BaseParam {

  private static final long serialVersionUID = -9153801716112918626L;

  @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
  private Long id; // user's ID

  @ApiModelProperty(value = "username", required = true, notes = "usr must greater than or equal to 4 and less than or equal to 50.")
  @NotNullField(value = OperationType.CREATE, message = "usr cannot be null.")
  @SizeField(min = 4, max = 50, value = OperationType.CREATE, message = "usr must greater than or equal to 4 and less than or equal to 50.")
  private String usr; // username

  @ApiModelProperty(value = "password", required = true, notes = "pwd must greater than or equal to 4 and less than or equal to 16.")
  @NotNullField(value = OperationType.CREATE, message = "pwd cannot be null.")
  @SizeField(min = 4, max = 16, value = OperationType.CREATE, message = "pwd must greater than or equal to 4 and less than or equal to 16.")
  private String pwd; // password

  @ApiModelProperty(value = "user's name")
  private String name; // user's name

  private String description;

  @ApiModelProperty(value = "ids of roles", example = "1,2,3", notes = "separated by comma and no space.")
  private String roleIds; // role ids string

  public UserParam() {}

  public UserParam(Long id) {
    this.id = id;
  }

  public UserParam(String usr) {
    this.usr = usr;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsr() {
    return usr;
  }

  public void setUsr(String usr) {
    this.usr = usr;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(String roleIds) {
    this.roleIds = roleIds;
  }
}
