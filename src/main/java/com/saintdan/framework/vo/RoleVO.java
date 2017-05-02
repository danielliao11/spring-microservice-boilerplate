package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.Set;

/**
 * VO for {@link com.saintdan.framework.po.Resource}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class RoleVO implements Serializable {

  private static final long serialVersionUID = 1444065316565469644L;

  private Long id;

  private String name;

  private String description;

  private Set<ResourceVO> resources;

  @Override public String toString() {
    final StringBuffer sb = new StringBuffer("RoleVO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append('}');
    return sb.toString();
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

  public Set<ResourceVO> getResources() {
    return resources;
  }

  public void setResources(Set<ResourceVO> resources) {
    this.resources = resources;
  }
}
