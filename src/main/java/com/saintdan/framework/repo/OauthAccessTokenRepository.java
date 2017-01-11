package com.saintdan.framework.repo;

import com.saintdan.framework.po.OauthAccessToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link OauthAccessToken}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 26/10/2016
 * @since JDK1.8
 */
public interface OauthAccessTokenRepository extends JpaRepository<OauthAccessToken, String> {

  Optional<OauthAccessToken> findByUserName(String userName);
}
