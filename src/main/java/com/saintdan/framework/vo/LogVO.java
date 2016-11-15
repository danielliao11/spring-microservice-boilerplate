package com.saintdan.framework.vo;

import com.saintdan.framework.enums.OperationType;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * VO for {@link com.saintdan.framework.po.Log}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public class LogVO implements Serializable {

  private static final long serialVersionUID = -8802363013216964724L;

  private String username;

  private String ip;

  private OperationType type;

  private LocalDateTime createDate;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public OperationType getType() {
    return type;
  }

  public void setType(OperationType type) {
    this.type = type;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public String getLogType() {
    return getType().name();
  }
}
