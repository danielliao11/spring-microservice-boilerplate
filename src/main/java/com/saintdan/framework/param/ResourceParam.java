package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.enums.OperationType;

/**
 * Param bean for {@link com.saintdan.framework.domain.ResourceDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class ResourceParam extends BaseParam {

  private static final long serialVersionUID = 8542867394907970893L;

  @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
  private Long id; // role's ID.

  @NotNullField(value = OperationType.CREATE, message = "name cannot be null.")
  private String name; // role's name

  private String description;

  public ResourceParam() {}

  public ResourceParam(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

}
