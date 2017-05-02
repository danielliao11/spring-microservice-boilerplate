package com.saintdan.framework.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Oauth access token.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 26/10/2016
 * @since JDK1.8
 */
@Entity @Table(name = "oauth_access_token") public class OauthAccessToken {

  @Id
  private String tokenId;

  private String userName;

  private String clientId;

  @Override public String toString() {
    final StringBuffer sb = new StringBuffer("OauthAccessToken{");
    sb.append("tokenId='").append(tokenId).append('\'');
    sb.append(", userName='").append(userName).append('\'');
    sb.append(", clientId='").append(clientId).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public String getId() {
    return tokenId;
  }

  public void setId(String id) {
    this.tokenId = id;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
}
