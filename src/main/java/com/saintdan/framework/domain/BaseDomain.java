package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.CustomRepository;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.BeanUtils;
import com.saintdan.framework.tools.ErrorMsgHelper;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract base service implement.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public abstract class BaseDomain<T, ID extends Serializable> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create <T> by param.
   *
   * @param voType      VO of some class
   * @param inputParam  input param
   * @param currentUser current user
   * @param <VO>        VO extends to ResultVO
   * @return VO
   */
  @Transactional public <VO> VO create(Class<VO> voType, Object inputParam, User currentUser)
      throws Exception {
    T po = transformer.param2PO(getClassT(), inputParam, getClassT().newInstance(), currentUser);
    return createByPO(voType, po);
  }

  /**
   * Create <T> by PO.
   *
   * @param voType  VO of some class
   * @param inputPO input PO
   * @param <VO>    VO extends to ResultVO
   * @return VO
   */
  @Transactional public <VO> VO createByPO(Class<VO> voType, T inputPO) throws Exception {
    return transformer.po2VO(voType, createByPO(inputPO));
  }

  @Transactional public T createByPO(T inputPO) throws Exception {
    return repository.save(inputPO);
  }

  /**
   * Get all <T>.
   *
   * @return <T>s
   */
  @SuppressWarnings("unchecked")
  public <VO> List<VO> getAll(Specification<T> specification, Sort sort, Class<VO> voType)
      throws InstantiationException, IllegalAccessException {
    List pos = repository.findAll(specification, sort);
    if (pos.isEmpty()) {
      return null;
    }
    return transformer.pos2VOs(voType, pos);
  }

  /**
   * Get page of <T>.
   *
   * @param specification {@link Specification}
   * @param pageable      pageable
   * @param voType        VO of some class
   * @return page of <T>
   */
  @SuppressWarnings("unchecked")
  public Page getPage(Specification<T> specification, Pageable pageable, Class voType)
      throws InstantiationException, IllegalAccessException {
    Page<T> poPage = repository.findAll(specification, pageable);
    if (poPage.getSize() == 0) {
      return null;
    }
    return transformer.poPage2VO(transformer.pos2VOs(voType, poPage.getContent()),
        pageable, poPage.getTotalElements());
  }

  /**
   * Get page of <T>.
   *
   * @param ids ids
   * @return page of <T>
   */
  public List<T> getAllByIds(List<ID> ids) throws Exception {
    return ids.stream()
        .map(id -> repository.findById(id).orElse(null))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  /**
   * Get <T> by id.
   *
   * @param id     id
   * @param voType VO of some class
   * @param <VO>   VO extends to ResultVO
   * @return <T>
   */
  @SuppressWarnings("unchecked")
  public <VO> VO getById(Long id, Class<VO> voType) throws Exception {
    return findById(id) == null ? null : transformer.po2VO(voType, findById(id));
  }

  /**
   * Update <T> by param.
   *
   * @param voType     VO of some class
   * @param inputParam input param
   * @param <VO>       VO extends to ResultVO
   * @return VO
   */
  @Transactional public <VO> VO update(Class<VO> voType, Object inputParam) throws Exception {
    T po = findByIdParam(inputParam);
    if (po == null) {
      throw new CommonsException(ErrorType.SYS0122,
          ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, voType.getName(), CommonsConstant.ID));
    }
    BeanUtils.copyPropertiesIgnoreNull(inputParam, po);
    return updateByPO(voType, po);
  }

  /**
   * Update <T> by param.
   *
   * @param voType  VO of some class
   * @param inputPO input PO
   * @param <VO>    VO extends to ResultVO
   * @return VO
   */
  @Transactional public <VO> VO updateByPO(Class<VO> voType, T inputPO) throws Exception {
    return transformer.po2VO(voType, updateByPO(inputPO));
  }


  /**
   * Update <T> by param.
   *
   * @param po input PO
   * @return VO
   */
  @Transactional public T updateByPO(T po) throws Exception {
    return repository.save(po);
  }


  /**
   * Delete <T>, update valid flag to invalid.
   *
   * @param inputParam input param
   */
  @Transactional public void delete(Object inputParam) throws Exception {
    T po = findByIdParam(inputParam);
    repository.save(setInvalid(po));
  }

  /**
   * update valid flag to invalid.by id
   */
  @Transactional public void deleteById(Long id) throws Exception {
    T po = findById(id);
    repository.save(setInvalid(po));
  }

  @SuppressWarnings("unchecked")
  @Transactional public void deepDelete(Long id) throws Exception {
    repository.deleteById((ID) id);
  }

  /**
   * update valid flag to invalid.by ids
   */
  @Transactional public void deleteByIds(String ids) throws RuntimeException {
    transformer.idsStr2List(ids).forEach(id -> {
      try {
        this.deleteById(id);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  /**
   * Find class by id.
   *
   * @param inputParam id
   * @return class
   */
  @SuppressWarnings("unchecked")
  public T findByIdParam(Object inputParam) throws Exception {
    Field idField = inputParam.getClass().getDeclaredField(CommonsConstant.ID);
    idField.setAccessible(true);
    return repository.findById((ID) idField.get(inputParam)).orElse(null);
  }

  @SuppressWarnings("unchecked")
  public T findById(Long id) {
    return repository.findById((ID) id).orElse(null);
  }

  /**
   * Get class of <T>
   *
   * @return class of <T>
   */
  @SuppressWarnings("unchecked")
  protected Class<T> getClassT() throws Exception {
    Type type = getClass().getGenericSuperclass();
    return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
  }

  private final CustomRepository<T, ID> repository;
  protected final Transformer transformer;

  @Autowired public BaseDomain(CustomRepository<T, ID> repository, Transformer transformer) {
    Assert.defaultNotNull(repository);
    Assert.defaultNotNull(transformer);
    this.repository = repository;
    this.transformer = transformer;
  }

  /**
   * Set invalid flag
   *
   * @param po po
   * @return po with invalid flag
   */
  private T setInvalid(T po) throws Exception {
    Field validFlagField = po.getClass().getDeclaredField(CommonsConstant.VALID_FLAG);
    validFlagField.setAccessible(true);
    validFlagField.set(po, ValidFlag.INVALID);
    return po;
  }
}
