package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * User's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
public interface UserRepository extends RepositoryWithoutDelete<User, Long> {

  Optional<User> findByUsr(String usr);

  List<User> findAllByValidFlag(ValidFlag validFlag);

  Page<User> findAllByValidFlag(Pageable pageable, ValidFlag validFlag);

  @Modifying
  @Query("update User u set u.pwd=?1 where u.id=?2")
  void updatePwdFor(String pwd, Long id);

  @Modifying
  @Query("update User u set u.validFlag=?1 where u.id=?2")
  void updateValidFlagFor(ValidFlag validFlag, Long id);

}
