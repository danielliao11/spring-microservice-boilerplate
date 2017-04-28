package com.saintdan.framework.repo;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

/**
 * Custom repository.
 * Extends the {@link Repository}
 * <p>
 * <p>
 * Hide the delete interface.
 * </p>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/29/15
 * @since JDK1.8
 */
@NoRepositoryBean public interface CustomRepository<T, ID extends Serializable>
    extends PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {

  /**
   * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
   *
   * @param entity PO
   * @return the saved entity
   */
  <S extends T> S save(S entity);

  /**
   * Saves all given entities.
   *
   * @param entities POs
   * @return the saved entities
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  <S extends T> Iterable<S> save(Iterable<S> entities);

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal null} if none found
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  Optional<T> findById(ID id);

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return true if an entity with the given id exists, {@literal false} otherwise
   * @throws IllegalArgumentException if {@code id} is {@literal null}
   */
  boolean exists(ID id);

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  List<T> findAll();

  /**
   * Returns all instances of the type with the given IDs.
   *
   * @param ids PO's ids
   * @return POs
   */
  List<T> findAll(Iterable<ID> ids);

  /**
   * Returns all instances of the type with the given pageable.
   *
   * @param pageable pageable
   * @return page
   */
  Page<T> findAll(Pageable pageable);

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities
   */
  long count();

}
