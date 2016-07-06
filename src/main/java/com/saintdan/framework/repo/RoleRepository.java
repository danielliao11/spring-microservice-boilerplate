package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Role's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface RoleRepository extends RepositoryWithoutDelete<Role, Long> {

  Optional<Role> findByName(String name);

  List<Role> findAllByValidFlag(ValidFlag validFlag);

  Page<Role> findAllByValidFlag(Pageable pageable, ValidFlag validFlag);

  @Modifying
  @Query("update Role r set r.validFlag=?1 where r.id=?2")
  void updateValidFlagFor(ValidFlag validFlag, Long id);
}
