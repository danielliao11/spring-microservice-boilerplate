package com.saintdan.framework.common.domain;

import com.github.pagehelper.PageHelper;
import com.saintdan.framework.common.constant.CommonsConstant;
import com.saintdan.framework.common.mapper.CommonMapper;
import com.saintdan.framework.common.param.BaseParam;
import com.saintdan.framework.common.tools.SpringContextUtils;
import com.saintdan.framework.common.vo.Page;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
public abstract class BaseDomain<M extends CommonMapper<T>, T>  {

  // =====================
  // ====== Create =======
  // =====================

  public int create(T entity) {
    setCreateInfo(entity);
    return mapper.insertSelective(entity);
  }

  // =====================
  // ====== Query ========
  // =====================

  public T findOne(T entity) {
    return mapper.selectOne(entity);
  }

  public T findById(Object id) {
    return mapper.selectByPrimaryKey(id);
  }

  public List<T> findAll() {
    return mapper.selectAll();
  }

  public List<T> findAll(T entity) {
    return mapper.select(entity);
  }

  public List<T> findAllByExample(Object example) {
    return mapper.selectByExample(example);
  }

  public int count(Object example) {
    return mapper.selectCountByExample(example);
  }

  @SuppressWarnings("unchecked")
  public Page<T> page(BaseParam param) {
    Class<T> clazz = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    Example example = new Example(clazz);
    param2Criteria(param, example);
    return page(param.getPageNo(), param.getPageSize(), example);
  }

  public Page<T> page(int pageNo, int pageSize, Example example) {
    com.github.pagehelper.Page<Object> result = PageHelper.startPage(pageNo, pageSize);
    List<T> list = this.findAllByExample(example);
    return Page.<T>builder().total(result.getTotal()).contents(list).build();
  }

  /**
   * Convert param to query example
   *
   * @param param param
   * @param example query example
   */
  public void param2Criteria(BaseParam param, Example example) {
    if (param.getClass().getFields().length > 0) {
      Example.Criteria criteria = example.createCriteria();
      Arrays.stream(param.getClass().getFields())
          .forEach(field -> {
            if (param.getBaseFields().containsKey(field.getName())) {
              String s = field.toString();
              if (s.contains("[") || s.contains("]")) {
                // sort field -> crtTime:[desc],id:[asc]
                Arrays.asList(s.split(",")).forEach(sortStr -> {
                  // sortStr -> crtTime:[desc]
                  String[] sortArray = sortStr.split(":");
                  if ("[desc]".equals(sortArray[1].trim())) {
                    example.orderBy(sortArray[0].trim()).desc();
                  } else if ("[asc]".equals(sortArray[1].trim())) {
                    example.orderBy(sortArray[0].trim()).asc();
                  }
                });
              }
            } else {
              criteria.andLike(field.getName(), "%" + field.toString() + "%");
            }
          });
    }
  }

  // =====================
  // ====== Update =======
  // =====================

  public int update(T entity) {
    setModifyInfo(entity);
    return mapper.updateByPrimaryKey(entity);
  }

  public int updateSelective(T entity) {
    setModifyInfo(entity);
    return mapper.updateByPrimaryKeySelective(entity);
  }

  // =====================
  // ====== Delete =======
  // =====================

  public int delete(Object id) {
    return mapper.deleteByPrimaryKey(id);
  }

  protected M mapper;

  public BaseDomain() {
  }

  public void setMapper(M mapper) {
    this.mapper = mapper;
  }

  private void setCreateInfo(T entity) {
    String userId = SpringContextUtils.getUserID();
    try {
      BeanUtils.setProperty(entity, CommonsConstant.CREATED_BY, userId == null ? "0" : userId);
      BeanUtils.setProperty(entity, CommonsConstant.CREATED_AT, System.currentTimeMillis());
      BeanUtils.setProperty(entity, CommonsConstant.LAST_MODIFIED_BY, userId == null ? "0" : userId);
      BeanUtils.setProperty(entity, CommonsConstant.LAST_MODIFIED_At, System.currentTimeMillis());
      BeanUtils.setProperty(entity, CommonsConstant.VERSION, 0);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException ignore) {}
  }

  private void setModifyInfo(T entity) {
    String userId = SpringContextUtils.getUserID();
    try {
      BeanUtils.setProperty(entity, CommonsConstant.LAST_MODIFIED_BY, userId == null ? "0" : userId);
      BeanUtils.setProperty(entity, CommonsConstant.LAST_MODIFIED_At, System.currentTimeMillis());
      BeanUtils.setProperty(entity, CommonsConstant.VERSION, Integer.valueOf(BeanUtils.getProperty(entity, CommonsConstant.VERSION)) + 1);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException | NoSuchMethodException ignore) {}
  }
}
