package com.saintdan.framework.vo;

import java.io.Serializable;
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
public class ResourceVO implements Serializable {

  private static final long serialVersionUID = 8917291426920312742L;
  private Long id;
  private String name;
  private String description;
}
