package com.saintdan.framework.starter.mapper;

import com.saintdan.framework.common.mapper.CommonMapperWithoutId;
import com.saintdan.framework.starter.po.UserAndRole;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
public interface UserAndRoleMapper extends CommonMapperWithoutId<UserAndRole> {

  int deleteByUserId(@Param("userId") String userId);
  int deleteByRoleId(@Param("roleId") String roleId);
}
