package com.saintdan.framework.starter.po;

import com.saintdan.framework.common.param.BaseParam;
import com.saintdan.framework.common.tools.UUIDGenId;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * Authorized resources,provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Table(name = "resources")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource extends BaseParam implements GrantedAuthority {

  private static final long serialVersionUID = 6298843159549723556L;

  @Id
  @KeySql(genId = UUIDGenId.class)
  @Column(name = "id", updatable = false)
  private String id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Builder.Default
  @Column(name = "status")
  private Integer status = 1;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Long createdAt;

  @Column(name = "created_by", nullable = false, updatable = false)
  private String createdBy;

  @Column(name = "last_modified_at", nullable = false)
  private Long lastModifiedAt;

  @Column(name = "last_modified_by", nullable = false)
  private String lastModifiedBy;

  @Column(name = "version", nullable = false)
  private Integer version;

  @Override
  public String getAuthority() {
    return name;
  }
}
