package com.saintdan.framework.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Oauth access token.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 26/10/2016
 * @since JDK1.8
 */
@Entity
@Table(name = "oauth_access_token")
@Data
public class OauthAccessToken {

  @Id
  private String tokenId;
  private String userName;
  private String clientId;
}
