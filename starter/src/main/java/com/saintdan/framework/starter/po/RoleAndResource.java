package com.saintdan.framework.starter.po;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019-03-31
 * @since JDK1.8
 */
@Table(name = "roles_and_resources")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleAndResource implements Serializable {

  private static final long serialVersionUID = -4313811596176887621L;

  @Column(name = "role_id")
  private String roleId;

  @Column(name = "resource_id")
  private String resourceId;
}
