package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Resource;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  Optional<Resource> findByName(String name);

  Optional<Resource> findByPath(String path);

  List<Resource> findAllByValidFlag(ValidFlag validFlag);

  Page<Resource> findAllByValidFlag(Pageable pageable, ValidFlag validFlag);

  @Modifying
  @Query("update Resource r set r.validFlag=?1 where r.id=?2")
  void updateValidFlagFor(ValidFlag validFlag, Long id);
}
