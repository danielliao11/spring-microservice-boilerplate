package com.saintdan.framework.param;

import com.saintdan.framework.annotation.NotNullField;
import com.saintdan.framework.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Param bean for {@link com.saintdan.framework.domain.RoleDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
@Data @EqualsAndHashCode(callSuper = false)  @Builder @NoArgsConstructor @AllArgsConstructor
public class RoleParam extends BaseParam {

  private static final long serialVersionUID = 5027600216405994820L;

  @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
  private Long id; // role's ID.

  @NotNullField(value = OperationType.CREATE, message = "name cannot be null.")
  private String name; // role's name

  private String description;

  private String resourceIds; // group ids string

  public RoleParam(Long id) {
    this.id = id;
  }
}
