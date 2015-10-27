package com.saintdan.framework.repo;

import com.saintdan.framework.po.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Group's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface GroupRepository extends CrudRepository<Group, Long> {

    Group findByName(String name);

    Page<Group> findAll(Pageable pageable);
}
