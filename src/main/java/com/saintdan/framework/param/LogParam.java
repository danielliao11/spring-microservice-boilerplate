package com.saintdan.framework.param;

import com.saintdan.framework.enums.OperationType;

import java.io.Serializable;

/**
 * Param bean for {@link com.saintdan.framework.domain.LogDomain}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
public class LogParam implements Serializable {

  private static final long serialVersionUID = 891050528216283300L;

  private String ip;

  private OperationType type;

  private String clientId;

  private String accessResource;

  public LogParam() {}

  public LogParam(String ip, OperationType type, String clientId, String accessResource) {
    this.ip = ip;
    this.type = type;
    this.clientId = clientId;
    this.accessResource = accessResource;
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

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getAccessResource() {
    return accessResource;
  }

  public void setAccessResource(String accessResource) {
    this.accessResource = accessResource;
  }
}
