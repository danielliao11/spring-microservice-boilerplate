package com.saintdan.framework.repo;

import com.saintdan.framework.po.Role;
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

}
