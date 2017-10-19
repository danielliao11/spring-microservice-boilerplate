package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VO for {@link com.saintdan.framework.po.Resource}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleVO implements Serializable {

  private static final long serialVersionUID = 1444065316565469644L;
  private Long id;
  private String name;
  private String description;
  private Set<ResourceVO> resources;
}
