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
@Table(name = "users_and_roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAndRole implements Serializable {

  private static final long serialVersionUID = -1362883509788225299L;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "role_id")
  private String roleId;
}
