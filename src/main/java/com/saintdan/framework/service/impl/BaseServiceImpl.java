package com.saintdan.framework.service.impl;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */

import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.RepositoryWithoutDelete;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.ResultVO;
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
public abstract class BaseServiceImpl<T, ID extends Serializable> {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create <T> by param.
     *
     * @param voType        VO's class
     * @param inputParam    input param
     * @param currentUser   current user
     * @param <VO>          VO extends to ResultVO
     * @return              VO
     * @throws Exception
     */
    public <VO extends ResultVO> VO createByParam(Class<VO> voType, Object inputParam, User currentUser) throws Exception {
        T po = transformer.param2PO(getClassT(), inputParam, getClassT().newInstance(), currentUser);
        logHelper.logUsersOperations(LogType.CREATE, getClassT().getSimpleName(), currentUser);
        return transformer.po2VO(voType, repository.save(po),
                String.format(ControllerConstant.CREATE, getClassT().getSimpleName()));
    }

    /**
     * Create <T> by PO.
     *
     * @param voType        VO's class
     * @param inputPO       input PO
     * @param currentUser   current user
     * @param <VO>          VO extends to ResultVO
     * @return              VO
     * @throws Exception
     */
    public <VO extends ResultVO> VO createByPO(Class<VO> voType, T inputPO, User currentUser) throws Exception {
        logHelper.logUsersOperations(LogType.CREATE, getClassT().getSimpleName(), currentUser);
        return transformer.po2VO(voType, repository.save(inputPO),
                String.format(ControllerConstant.CREATE, getClassT().getSimpleName()));
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
            throw new CommonsException(ErrorType.SYS0120, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName()));
        }
        return transformer.pos2VO(ObjectsVO.class, pos, String.format(ControllerConstant.INDEX, getClassT().getSimpleName()));
    }

    /**
     * Get <T>'s page.
     *
     * @param pageable      pageable
     * @param voType        VO's class
     * @return              <T>'s page
     * @throws Exception
     */
    public PageVO getPage(Pageable pageable, Class voType) throws Exception {
        Page<T> poPage = repository.findAll(pageable);
        if (poPage.getSize() == 0) {
            // Throw po cannot find exception.
            throw new CommonsException(ErrorType.SYS0120, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName()));
        }
        return transformer.poPage2VO(
                transformer.poList2VOList(voType, (Iterable) poPage.getContent()),
                pageable, poPage.getTotalElements(),
                String.format(ControllerConstant.INDEX, getClassT().getSimpleName()));
    }

    /**
     * Get <T> by id.
     *
     * @param voType        VO's class
     * @param inputParam    input param
     * @param <VO>          VO extends to ResultVO
     * @return              <T>
     * @throws Exception
     */
    public <VO extends ResultVO> VO getById(Class<VO> voType, Object inputParam) throws Exception {
        Field idField = inputParam.getClass().getDeclaredField(CommonsConstant.ID);
        idField.setAccessible(true);
        T po = repository.findOne((ID) idField.get(inputParam));
        if (po == null) {
            // Throw po cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0120, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return transformer.po2VO(voType, po, String.format(ControllerConstant.SHOW, getClassT().getSimpleName()));
    }

    /**
     * Get <T> by name.
     *
     * @param voType        VO's class
     * @param inputParam    input param
     * @param <VO>          VO extends to ResultVO
     * @return              <T>
     * @throws Exception
     */
    public <VO extends ResultVO> VO getByName(Class<VO> voType, Object inputParam) throws Exception {
        Field nameField = inputParam.getClass().getDeclaredField(CommonsConstant.NAME);
        nameField.setAccessible(true);
        T po = repository.findByName((String) nameField.get(inputParam));
        if (po == null) {
            // Throw po cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0120, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), CommonsConstant.NAME));
        }
        return transformer.po2VO(voType, po, String.format(ControllerConstant.SHOW, getClassT().getSimpleName()));
    }

    /**
     * Update <T> by param.
     *
     * @param voType        VO's class
     * @param inputParam    input param
     * @param currentUser   current user
     * @param <VO>          VO extends to ResultVO
     * @return              VO
     * @throws Exception
     */
    public <VO extends ResultVO> VO updateByParam(Class<VO> voType, Object inputParam, User currentUser) throws Exception {
        Field idField = inputParam.getClass().getDeclaredField(CommonsConstant.ID);
        idField.setAccessible(true);
        T po = repository.findOne((ID) idField.get(inputParam));
        if (po == null) {
            // Throw po cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0120, ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), CommonsConstant.ID));
        }
        logHelper.logUsersOperations(LogType.UPDATE, getClassT().getSimpleName(), currentUser);
        return transformer.po2VO(voType, repository.save(transformer.param2PO(getClassT(), inputParam, getClassT().newInstance(), currentUser)),
                String.format(ControllerConstant.UPDATE, getClassT().getSimpleName()));
    }

    /**
     * Update <T> by param.
     *
     * @param voType        VO's class
     * @param inputPO       input PO
     * @param currentUser   current user
     * @param <VO>          VO extends to ResultVO
     * @return              VO
     * @throws Exception
     */
    public <VO extends ResultVO> VO updateByPO(Class<VO> voType, T inputPO, User currentUser) throws Exception {
        logHelper.logUsersOperations(LogType.UPDATE, getClassT().getSimpleName(), currentUser);
        return transformer.po2VO(voType, repository.save(inputPO),
                String.format(ControllerConstant.UPDATE, getClassT().getSimpleName()));
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
     * Get <T>'s class
     *
     * @return      <T>'s class
     * @throws Exception
     */
    protected Class<T> getClassT() throws Exception {
        Type type = getClass().getGenericSuperclass();
        return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
    }
}
