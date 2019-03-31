package com.saintdan.framework.starter.mapper;

import com.saintdan.framework.common.mapper.CommonMapper;
import com.saintdan.framework.starter.po.Client;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
public interface ClientMapper extends CommonMapper<Client> {

  Client findByClient(@Param("client") String client, @Param("status") Integer status);
}
