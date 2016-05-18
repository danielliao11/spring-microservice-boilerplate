package com.saintdan.framework.domain;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */

import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.RepositoryWithoutDelete;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Abstract base service implement.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/21/15
 * @since JDK1.8
 */
public abstract class BaseDomain<T, ID extends Serializable> {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create <T> by param.
     *
     * @param voType        VO of some class
     * @param inputParam    input param
     * @param currentUser   current user
     * @param <VO>          VO extends to ResultVO
     * @return              VO
     * @throws Exception
     */
    public <VO> VO create(Class<VO> voType, Object inputParam, User currentUser) throws Exception {
        T po = transformer.param2PO(getClassT(), inputParam, getClassT().newInstance(), currentUser);
        return createByPO(voType, po, currentUser);
    }

    /**
     * Create <T> by PO.
     *
     * @param voType        VO of some class
     * @param inputPO       input PO
     * @param currentUser   current user
     * @param <VO>          VO extends to ResultVO
     * @return              VO
     * @throws Exception
     */
    public <VO> VO createByPO(Class<VO> voType, T inputPO, User currentUser) throws Exception {
        logHelper.logUsersOperations(LogType.CREATE, getClassT().getSimpleName(), currentUser);
        return transformer.po2VO(voType, repository.save(inputPO));
    }

    /**
     * Get all <T>.
     *
     * @return              <T>s
     * @throws Exception
     */
    public ObjectsVO getAll() throws Exception {
        Iterable pos = repository.findAll();
        if (((List) pos).isEmpty()) {
            // Throw po cannot find exception.
            throw new CommonsException(ErrorType.SYS0121, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0121, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.pos2VO(ObjectsVO.class, pos);
    }

    /**
     * Get page of <T>.
     *
     * @param pageable      pageable
     * @param voType        VO of some class
     * @return              page of <T>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public PageVO getPage(Pageable pageable, Class voType) throws Exception {
        Page<T> poPage = repository.findAll(pageable);
        if (poPage.getSize() == 0) {
            // Throw po cannot find exception.
            throw new CommonsException(ErrorType.SYS0121, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0121, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.poPage2VO(
                transformer.poList2VOList(voType, (Iterable) poPage.getContent()),
                pageable, poPage.getTotalElements());
    }

    /**
     * Get <T> by id.
     *
     * @param voType        VO of some class
     * @param inputParam    input param
     * @param <VO>          VO extends to ResultVO
     * @return              <T>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <VO> VO getById(Class<VO> voType, Object inputParam) throws Exception {
        Field idField = inputParam.getClass().getDeclaredField(CommonsConstant.ID);
        idField.setAccessible(true);
        T po = repository.findOne((ID) idField.get(inputParam));
        if (po == null) {
            // Throw po cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return transformer.po2VO(voType, po);
    }

    /**
     * Update <T> by param.
     *
     * @param voType        VO of some class
     * @param inputParam    input param
     * @param currentUser   current user
     * @param <VO>          VO extends to ResultVO
     * @return              VO
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <VO> VO update(Class<VO> voType, Object inputParam, User currentUser) throws Exception {
        Field idField = inputParam.getClass().getDeclaredField(CommonsConstant.ID);
        idField.setAccessible(true);
        T po = repository.findOne((ID) idField.get(inputParam));
        if (po == null) {
            // Throw po cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return updateByPO(voType, po, currentUser);
    }

    /**
     * Update <T> by param.
     *
     * @param voType        VO of some class
     * @param inputPO       input PO
     * @param currentUser   current user
     * @param <VO>          VO extends to ResultVO
     * @return              VO
     * @throws Exception
     */
    public <VO> VO updateByPO(Class<VO> voType, T inputPO, User currentUser) throws Exception {
        logHelper.logUsersOperations(LogType.UPDATE, getClassT().getSimpleName(), currentUser);
        return transformer.po2VO(voType, repository.save(inputPO));
    }

    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private RepositoryWithoutDelete<T,ID> repository;

    @Autowired
    protected LogHelper logHelper;

    @Autowired
    private Transformer transformer;


    /**
     * Get class of <T>
     *
     * @return      class of <T>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected Class<T> getClassT() throws Exception {
        Type type = getClass().getGenericSuperclass();
        return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
    }
}
