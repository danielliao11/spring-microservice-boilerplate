package com.saintdan.framework.repo;


import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Custom repository.
 * Extends the {@link JpaRepository} and {@link JpaSpecificationExecutor}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/29/15
 * @since JDK1.8
 */
@NoRepositoryBean public interface CustomRepository<T, ID extends Serializable>
    extends JpaSpecificationExecutor<T>, JpaRepository<T, ID> {

}
