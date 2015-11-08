package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Resource's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface ResourceRepository extends RepositoryWithoutDelete<Resource, Long> {

    @EntityGraph(value = "Resource.resources", type = EntityGraph.EntityGraphType.FETCH)
    Resource findByName(String name);

    @EntityGraph(value = "Resource.resources", type = EntityGraph.EntityGraphType.FETCH)
    Page<Resource> findAll(Pageable pageable);

    // ------------------------
    // OVERRIDE INTERFACES
    // ------------------------

    @Override
    @EntityGraph(value = "Resource.resources", type = EntityGraph.EntityGraphType.FETCH)
    Resource findOne(Long aLong);

    @Override
    @EntityGraph(value = "Resource.resources", type = EntityGraph.EntityGraphType.FETCH)
    Iterable<Resource> findAll();

    @Override
    @EntityGraph(value = "Resource.resources", type = EntityGraph.EntityGraphType.FETCH)
    Iterable<Resource> findAll(Iterable<Long> longs);

    @Modifying
    @Query("update Resource r set r.validFlag=?1 where r.id=?2")
    void updateValidFlagFor(ValidFlag validFlag, Long id);
}
