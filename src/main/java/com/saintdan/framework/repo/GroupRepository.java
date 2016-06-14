package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Group;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Group's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface GroupRepository extends RepositoryWithoutDelete<Group, Long> {

  Group findByName(String name);

  @Modifying
  @Query("update Group g set g.validFlag=?1 where g.id=?2")
  void updateValidFlagFor(ValidFlag validFlag, Long id);
}
