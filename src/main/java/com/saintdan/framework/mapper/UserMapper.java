package com.saintdan.framework.mapper;

import com.saintdan.framework.po.User;
import com.saintdan.framework.tools.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
public interface UserMapper extends CommonMapper<User> {

  @Select("select * from users where usr=#{usr} and status=#{status}")
  User findByUsr(@Param("usr") String usr, @Param("status") Integer status);
}
