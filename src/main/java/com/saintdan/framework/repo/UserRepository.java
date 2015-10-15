package com.saintdan.framework.repo;

import com.saintdan.framework.po.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * User repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsr(String usr);

}
