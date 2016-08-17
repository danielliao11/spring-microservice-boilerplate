package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Group;
import java.util.Optional;

/**
 * Group's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface GroupRepository extends RepositoryWithoutDelete<Group, Long> {

  Optional<Group> findByNameAndValidFlag(String name, ValidFlag validFlag);

}
