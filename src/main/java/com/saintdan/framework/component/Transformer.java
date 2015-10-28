package com.saintdan.framework.component;

import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Ids string transform to iterable helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/19/15
 * @since JDK1.8
 */
@Component
public class Transformer {

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

    /**
     * Transform object iterable to hash set.
     *
     * @param objects       object iterable
     * @return              object hash set
     */
    public <T> Set<T> iterable2Set(Iterable<T> objects) {
        Set<T> objectSet = new HashSet<>();
        for (T object : objects) {
            objectSet.add(object);
        }
        return objectSet;
    }

    /**
     * Transform PO page to PageVO.
     *
     * @param content               page content
     * @param pageable              page init
     * @param totalElements         page total
     * @return                      PageVO
     */
    public PageVO poPage2VO(List content, Pageable pageable, Long totalElements, String msg) {
        Page page = new PageImpl<>(content, pageable, totalElements);
        PageVO pageVO = new PageVO();
        pageVO.setPage(page);
        pageVO.setMessage(msg);
        return (PageVO) resultHelper.sucessResp(pageVO);
    }

    /**
     * Transform VO list to objects' VO.
     *
     * @param list          VO list
     * @param msg           return message
     * @return              objects' VO
     */
    public ObjectsVO voList2ObjectsVO(List list, String msg) {
        ObjectsVO vo = new ObjectsVO();
        vo.setObjectsVOList(list);
        if (StringUtils.isBlank(msg)) {
            return vo;
        }
        vo.setMessage(msg);
        return vo;
    }

    @Autowired
    private ResultHelper resultHelper;
}
