package com.saintdan.framework.repo;

import com.saintdan.framework.po.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Resource repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface ResourceRepository extends CrudRepository<Role, Long> {

}
