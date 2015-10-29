package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Role's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
@Transactional
public interface RoleRepository extends RepositoryWithoutDelete<Role, Long> {

    Role findByName(String name);

    Page<Role> findAll(Pageable pageable);

    @Modifying
    @Query("update Role r set r.validFlag=?1 where r.id=?2")
    void updateValidFlagFor(ValidFlag validFlag, Long id);
}
