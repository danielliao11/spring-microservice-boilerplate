package com.saintdan.framework.domain;

import com.saintdan.framework.mapper.UserMapper;
import com.saintdan.framework.param.BaseParam;
import com.saintdan.framework.po.User;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
@Service
public class UserDomain extends BaseDomain<UserMapper, BaseParam, User> {
}
