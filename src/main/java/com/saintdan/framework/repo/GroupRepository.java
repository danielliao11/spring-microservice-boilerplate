package com.saintdan.framework.repo;

import com.saintdan.framework.po.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Group repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface GroupRepository extends CrudRepository<Role, Long> {

}
