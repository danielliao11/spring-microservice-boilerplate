package com.saintdan.framework.common.tools;

import com.saintdan.framework.common.constant.CommonsConstant;
import java.util.UUID;
import tk.mybatis.mapper.genid.GenId;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
public class UUIDGenId implements GenId<String> {

    @Override public String genId(String s, String s1) {
        return UUID.randomUUID().toString().replace(CommonsConstant.LINK, CommonsConstant.BLANK);
    }
}
