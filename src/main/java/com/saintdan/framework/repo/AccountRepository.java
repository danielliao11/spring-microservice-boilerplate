package com.saintdan.framework.repo;

import com.saintdan.framework.po.Account;
import java.util.Optional;

/**
 * Repository for {@link Account}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/02/2017
 * @since JDK1.8
 */
public interface AccountRepository extends CustomRepository<Account, Long> {

  Optional<Account> findByAccount(String account);
}
