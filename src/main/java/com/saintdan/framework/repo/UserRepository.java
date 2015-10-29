package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * User's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Transactional
public interface UserRepository extends RepositoryWithoutDelete<User, Long> {

	User findByUsr(String usr);

    Page<User> findAll(Pageable pageable);

    @Modifying
    @Query("update User u set u.pwd=?1 where u.id=?2")
    void updatePwdFor(String pwd, Long id);

    @Modifying
    @Query("update User u set u.validFlag=?1 where u.id=?2")
    void updateValidFlagFor(ValidFlag validFlag, Long id);

}
