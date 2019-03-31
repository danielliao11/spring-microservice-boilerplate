package com.saintdan.framework.common.mapper;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

/**
 * Common mapper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
public interface CommonMapper<T> extends SelectByIdsMapper<T>, Mapper<T>, InsertListMapper<T> {
}
