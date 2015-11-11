package com.saintdan.framework.component;

import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Ids string transform to iterable helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/19/15
 * @since JDK1.8
 */
@Component
public class Transformer {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

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
        return (PageVO) resultHelper.successResp(pageVO);
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

    /**
     * Transform param to PO.
     *
     * @param type          class type
     * @param param         param
     * @param po            PO
     * @param currentUser   current user
     * @param <T>           class
     * @return              PO
     * @throws Exception
     */
    public <T> T param2PO(Class<T> type, Object param, T po, User currentUser) throws Exception {
        // Init createdBy, lastModifiedBy
        Long createdBy, lastModifiedBy;
        // Init createdDate
        Date createdDate = new Date();
        // Init transformer
        Field idField = type.getDeclaredField("id");
        idField.setAccessible(true);
        Field createdByField = type.getDeclaredField("createdBy");
        createdByField.setAccessible(true);
        Field createdDateField = type.getDeclaredField("createdDate");
        createdDateField.setAccessible(true);
        Field lastModifiedByField = type.getDeclaredField("lastModifiedBy");
        lastModifiedByField.setAccessible(true);
        // Log operation.
        if (idField.get(po) == null) {
            createdBy = currentUser.getId();
            lastModifiedBy = createdBy;
        } else {
            createdBy = (Long) createdByField.get(po);
            createdDate = (Date) createdDateField.get(po);
            lastModifiedBy = currentUser.getId();
        }
        // Set param.
        BeanUtils.copyProperties(param, po);
        createdByField.set(po, createdBy);
        createdDateField.set(po, createdDate);
        lastModifiedByField.set(po,lastModifiedBy);
        return po;
    }


    /**
     * Transform PO to VO.
     *
     * @param po        PO
     * @param msg       return message
     * @return VO
     */
    public <T extends ResultVO> T po2VO(Class<T> type, Object po, String msg) throws Exception {
        T vo = type.newInstance();
        BeanUtils.copyProperties(po, vo);
        if (StringUtils.isBlank(msg)) {
            return vo;
        }
        vo.setMessage(msg);
        // Return success result.
        return (T) resultHelper.successResp(vo);
    }


    /**
     * Transform PO to VO
     *
     * @param pos       PO
     * @param msg       return message
     * @return VO
     */
    public ObjectsVO pos2VO(Class<? extends ResultVO> type, Iterable pos, String msg) throws Exception {
        List objList = poList2VOList(type, pos);
        ObjectsVO vos = voList2ObjectsVO(objList, msg);
        return (ObjectsVO) resultHelper.successResp(vos);
    }

    /**
     * Transform PO list to VO list.
     *
     * @param pos       PO list
     * @return          VO list
     */
    public List<?> poList2VOList(Class<? extends ResultVO> type, Iterable pos) throws Exception {
        List voList = new ArrayList();
        for (Object po : pos) {
            Object vo =  po2VO(type, po, "");
            voList.add(vo);
        }
        return voList;
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private ResultHelper resultHelper;
}
