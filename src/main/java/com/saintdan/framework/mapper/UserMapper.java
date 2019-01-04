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

  @Select("select u.id, u.name, u.usr, u.pwd, u.is_account_non_expired_alias, u.is_account_non_locked_alias, u.is_credentials_non_expired_alias, u.is_enabled_alias, u.status, u.description, u.last_login_at, u.ip, u.created_at, u.created_by, u.last_modified_at, u.last_modified_by, u.version, u.authority_str " +
      "from users u " +
      "left join accounts a " +
      "on u.id = a.user_id " +
      "where a.account = #{usr} and u.status = #{status}")
  User findByUsr(@Param("usr") String usr, @Param("status") Integer status);
}
