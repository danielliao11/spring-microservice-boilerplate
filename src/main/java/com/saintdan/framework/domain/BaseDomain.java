package com.saintdan.framework.domain;

import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.ResultVO;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * Base service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */
public interface BaseDomain<T, ID extends Serializable> {

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
    <VO extends ResultVO> VO create(Class<VO> voType, Object inputParam, User currentUser) throws Exception;

    /**
     * Get all <T>.
     *
     * @return              <T>s
     * @throws Exception
     */
    ObjectsVO getAll() throws Exception;

    /**
     * Get <T>'s page.
     *
     * @param pageable      pageable
     * @param voType        VO's class
     * @return              <T>'s page
     * @throws Exception
     */
    PageVO getPage(Pageable pageable, Class voType) throws Exception;

    /**
     * Get <T> by id.
     *
     * @param voType        VO's class
     * @param inputParam    input param
     * @param <VO>          VO extends to ResultVO
     * @return              <T>
     * @throws Exception
     */
    <VO extends ResultVO> VO getById(Class<VO> voType, Object inputParam) throws Exception;

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
    <VO extends ResultVO> VO update(Class<VO> voType, Object inputParam, User currentUser) throws Exception;

}
