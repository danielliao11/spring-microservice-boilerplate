package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.User;
import java.util.Optional;


/**
 * User's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
public interface UserRepository extends CustomRepository<User, Long> {

  Optional<User> findByIdAndValidFlag(Long id, ValidFlag validFlag);

  Optional<User> findByUsrAndValidFlag(String usr, ValidFlag validFlag);
}
