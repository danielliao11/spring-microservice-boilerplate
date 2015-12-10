package com.saintdan.framework.repo;

import com.saintdan.framework.po.Resource;

/**
 * Resource's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface ResourceRepository extends RepositoryWithoutDelete<Resource, Long> {

    Resource findByName(String name);

}
