package com.saintdan.framework.repo;

import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.po.Resource;
import java.util.Optional;

/**
 * Resource's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface ResourceRepository extends CustomRepository<Resource, Long> {

  Optional<Resource> findByNameAndValidFlag(String name, ValidFlag validFlag);

}
