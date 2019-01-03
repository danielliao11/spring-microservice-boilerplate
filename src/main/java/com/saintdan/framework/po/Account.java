package com.saintdan.framework.po;

import com.saintdan.framework.tools.UUIDGenId;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * Account of user.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/02/2017
 * @since JDK1.8
 */
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

  private static final long serialVersionUID = -6004454109313475045L;

  @Id
  @KeySql(genId = UUIDGenId.class)
  @Column(name = "id", updatable = false)
  private String id;

  @Column(name = "account")
  private String account;

  @Column(name = "account_source_type")
  private Integer accountSourceType;

  @Column(name = "created_at", updatable = false)
  private Long createdAt;

  @Column(name = "created_by", nullable = false, updatable = false)
  private String createdBy;

  @Column(name = "last_modified_at", nullable = false)
  private Long lastModifiedAt;

  @Column(name = "last_modified_by", nullable = false)
  private String lastModifiedBy;

  @Column(name = "version", nullable = false)
  private Integer version;

  @Column(name = "user_id")
  private String userId;

  @Builder.Default
  @Column(name = "status", nullable = false)
  private Integer status = 0;
}
