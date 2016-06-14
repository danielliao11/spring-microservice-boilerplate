package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Client;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Client's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
@Transactional
public interface ClientRepository extends RepositoryWithoutDelete<Client, Long> {

  Client findByClientIdAlias(String clientIdAlias);

  @Modifying
  @Query("update Client c set c.validFlag=?1 where c.id=?2")
  void updateValidFlagFor(ValidFlag validFlag, Long id);
}
