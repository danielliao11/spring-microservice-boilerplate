package com.saintdan.framework.mapper;

import com.saintdan.framework.po.Client;
import com.saintdan.framework.tools.CommonMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
public interface ClientMapper extends CommonMapper<Client> {

  Client findByClient(@Param("client") String client, @Param("status") Integer status);
}
