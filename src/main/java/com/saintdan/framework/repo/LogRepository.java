package com.saintdan.framework.repo;

import com.saintdan.framework.po.Log;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Log's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public interface LogRepository extends PagingAndSortingRepository<Log, Long> {

}
