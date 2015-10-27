package com.saintdan.framework.repo;

import com.saintdan.framework.po.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Resource's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface ResourceRepository extends CrudRepository<Resource, Long> {

    Resource findByName(String name);

    Resource findByPath(String path);

    Page<Resource> findAll(Pageable pageable);
}
