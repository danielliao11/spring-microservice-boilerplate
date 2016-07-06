package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Client's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
public interface ClientRepository extends RepositoryWithoutDelete<Client, Long> {

  Optional<Client> findByClientIdAlias(String clientIdAlias);

  List<Client> findAllByValidFlag(ValidFlag validFlag);

  Page<Client> findAllByValidFlag(Pageable pageable, ValidFlag validFlag);

  @Modifying
  @Query("update Client c set c.validFlag=?1 where c.id=?2")
  void updateValidFlagFor(ValidFlag validFlag, Long id);
}
