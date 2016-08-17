package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Client;
import java.util.Optional;

/**
 * Client's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
public interface ClientRepository extends RepositoryWithoutDelete<Client, Long> {

  Optional<Client> findByClientIdAliasAndValidFlag(String clientIdAlias, ValidFlag validFlag);

}
