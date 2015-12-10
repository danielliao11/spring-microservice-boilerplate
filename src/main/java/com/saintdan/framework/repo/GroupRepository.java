package com.saintdan.framework.repo;

import com.saintdan.framework.po.Group;

/**
 * Group's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface GroupRepository extends RepositoryWithoutDelete<Group, Long> {

    Group findByName(String name);
}
