package com.saintdan.framework.po;

import com.saintdan.framework.tools.UUIDGenId;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * Authorized roles, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/15
 * @since JDK1.8
 */
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

  private static final long serialVersionUID = -5193344128221526323L;

  @Id
  @KeySql(genId = UUIDGenId.class)
  @Column(name = "id", updatable = false)
  private String id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Builder.Default
  @Column(name = "status", nullable = false)
  private int status = 0;

  @Column(name = "created_at", nullable = false, updatable = false)
  private long createdAt;

  @Column(name = "created_by", nullable = false, updatable = false)
  private String createdBy;

  @Column(name = "last_modified_at", nullable = false)
  private long lastModifiedAt;

  @Column(name = "last_modified_by", nullable = false)
  private String lastModifiedBy;

  @Version
  @Column(name = "version", nullable = false)
  private int version;
}
