package com.saintdan.framework.repo;

import com.saintdan.framework.po.Client;
import java.util.Optional;

/**
 * Client's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
public interface ClientRepository extends CustomRepository<Client, Long> {

  Optional<Client> findByClientIdAlias(String clientIdAlias);
}
