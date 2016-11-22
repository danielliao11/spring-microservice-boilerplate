package com.saintdan.framework.component;

import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.BeanUtils;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Ids string transform to iterable helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/19/15
 * @since JDK1.8
 */
@Component public class Transformer {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Transform ids string to iterable.
   *
   * @param idsStr ids String
   * @return ids iterable
   */
  public List<Long> idsStr2List(String idsStr) {
    return Arrays.stream(idsStr.split(",")).map(Long::valueOf).collect(Collectors.toList());
  }

  public String IdList2IdsStr(List<Long> list){
    return list.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  /**
   * Transform object list to hash set.
   *
   * @param objects object iterable
   * @return object hash set
   */
  public <T> Set<T> list2Set(List<T> objects) {
    return new HashSet<>(objects);
  }


  /**
   * Transform object list to hash set.
   *
   * @param objects object iterable
   * @return object hash set
   */
  public <T> List<T> set2List(Set<T> objects) {
    return new ArrayList<>(objects);
  }

  /**
   * Transform PO page to PageVO.
   *
   * @param content       page content
   * @param pageable      page init
   * @param totalElements page total
   * @return PageVO
   */
  @SuppressWarnings("unchecked")
  public Page poPage2VO(List content, Pageable pageable, Long totalElements) {
    return new PageImpl<>(content, pageable, totalElements);
  }

  /**
   * Transform param to PO.
   *
   * @param type        class type
   * @param param       param
   * @param po          PO
   * @param currentUser current user
   * @param <T>         class
   * @return PO
   * @throws Exception
   */
  public <T> T param2PO(Class<T> type, Object param, T po, User currentUser) throws Exception {
    // Init createdBy, lastModifiedBy
    Long createdBy;
    Long lastModifiedBy;
    // Init transformer
    Field idField = type.getDeclaredField(CommonsConstant.ID);
    idField.setAccessible(true);
    Field createdByField = type.getDeclaredField(CommonsConstant.CREATED_BY);
    createdByField.setAccessible(true);
    Field lastModifiedByField = type.getDeclaredField(CommonsConstant.LAST_MODIFIED_BY);
    lastModifiedByField.setAccessible(true);
    Field lastModifiedDateField = type.getDeclaredField(CommonsConstant.LAST_MODIFIED_DATE);
    lastModifiedDateField.setAccessible(true);
    LocalDateTime now = LocalDateTime.now();
    if (idField.get(po) == null) {
      createdBy = currentUser.getId();
      lastModifiedBy = createdBy;
    } else {
      createdBy = (Long) createdByField.get(po);
      lastModifiedBy = currentUser.getId();
    }
    // Set param.
    BeanUtils.copyPropertiesIgnoreNull(param, po);
    createdByField.set(po, createdBy);
    lastModifiedByField.set(po, lastModifiedBy);
    lastModifiedDateField.set(po, now);
    return po;
  }

  /**
   * Transform PO to VO
   *
   * @param pos PO
   * @return VO
   */
  @SuppressWarnings("unchecked")
  public List pos2VOs(Class<?> type, List pos) throws InstantiationException, IllegalAccessException {
    List voList = new ArrayList();
    for (Object po : pos) {
      Object vo = po2VO(type, po);
      voList.add(vo);
    }
    return voList;
  }

  /**
   * Transform PO to VO.
   *
   * @param po PO
   * @return VO
   */
  public <T> T po2VO(Class<T> clazz, Object po) throws InstantiationException, IllegalAccessException {
    T vo = clazz.newInstance();
    BeanUtils.copyPropertiesIgnoreNull(po, vo);
    return vo;
  }

}
