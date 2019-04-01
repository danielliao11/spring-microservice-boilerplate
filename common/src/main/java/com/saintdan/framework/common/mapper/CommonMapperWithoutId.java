package com.saintdan.framework.common.mapper;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019-04-02
 * @since JDK1.8
 */
public interface CommonMapperWithoutId<T> extends Mapper<T>, InsertListMapper<T> {
}
