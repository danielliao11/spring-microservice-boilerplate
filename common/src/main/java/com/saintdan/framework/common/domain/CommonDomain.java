package com.saintdan.framework.common.domain;

import com.saintdan.framework.common.mapper.CommonMapper;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/3/31
 * @since JDK1.8
 */
@Service("commonDomain")
public class CommonDomain<M extends CommonMapper<T>, T> extends BaseDomain<M, T> {
}
