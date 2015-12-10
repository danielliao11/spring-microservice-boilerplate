package com.saintdan.framework.repo;

import com.saintdan.framework.po.Client;
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

}
