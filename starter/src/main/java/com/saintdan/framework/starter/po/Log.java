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
import tk.mybatis.mapper.annotation.KeySql;

/**
 * Log, record users' behavior.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
@Table(name = "logs")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log extends BaseParam {

  private static final long serialVersionUID = 7088091769901805623L;

  @Id
  @KeySql(genId = UUIDGenId.class)
  @Column(name = "id", updatable = false)
  private String id;

  @Column(name = "ip", nullable = false)
  private String ip;

  @Column(name = "usr", nullable = false)
  private String usr;

  @Column(name = "client_id")
  private String clientId;

  @Column(name = "path")
  private String path;

  @Column(name = "method")
  private String method;

  @Column(name = "created_by", nullable = false)
  private String createdBy;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;
}