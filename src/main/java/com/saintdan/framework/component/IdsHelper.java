package com.saintdan.framework.component;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Ids string transform to iterable helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/19/15
 * @since JDK1.8
 */
@Component
public class IdsHelper {

    /**
     * Transform ids string to iterable.
     *
     * @param idsStr        ids String
     * @return              ids iterable
     */
    public Iterable<Long> idsStr2Iterable(String idsStr) {
        List<Long> ids = new ArrayList<>();
        // Transform ids string to ids array.
        String[] idsArray = idsStr.split(",");
        // Generate id list.
        for (String id : idsArray) {
            ids.add(Long.valueOf(id));
        }
        return ids;
    }
}
