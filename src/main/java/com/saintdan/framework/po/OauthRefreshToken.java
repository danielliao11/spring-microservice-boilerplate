package com.saintdan.framework.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 26/10/2016
 * @since JDK1.8
 */
@Entity @Table(name = "oauth_access_token") public class OauthRefreshToken {

  @Id
  private String tokenId;

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

}
