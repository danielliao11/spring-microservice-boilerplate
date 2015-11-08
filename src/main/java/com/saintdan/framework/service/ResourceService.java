package com.saintdan.framework.service;

import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ResourceParam;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.User;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.ResourceVO;
import org.springframework.data.domain.Pageable;

/**
 * Resource's Service.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface ResourceService extends BaseService<Resource, Long> {

    /**
     * Create new resource.
     *
     * @param currentUser   current user
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0111 resource already existing, name taken.
     */
    ResourceVO create(ResourceParam param, User currentUser) throws Exception;

    /**
     * Show all resources' VO.
     *
     * @return              resources' VO
     * @throws CommonsException        SYS0120 No resource exists.
     */
    ObjectsVO getAllResources() throws Exception;

    /**
     * Show resources' page VO.
     *
     * @param pageable      page
     * @return              resources' page VO
     * @throws CommonsException        SYS0120 No resource exists.
     */
    PageVO getPage(Pageable pageable) throws Exception;

    /**
     * Show resources by ids.
     *
     * @param ids           resources' ids
     * @return              resources' PO
     * @throws CommonsException        SYS0120 No resource exists.
     */
    Iterable<Resource> getResourcesByIds(Iterable<Long> ids) throws Exception;

    /**
     * Show resource's VO by resource's id.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0122 Cannot find any resource by id param.
     */
    ResourceVO getResourceById(ResourceParam param) throws Exception;

    /**
     * Show resource's VO by resource's name.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0122 Cannot find any resource by name param.
     */
    ResourceVO getResourceByName(ResourceParam param) throws Exception;

    /**
     * Show resource's VO by resource's path.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0122 Cannot find any resource by path param.
     */
    ResourceVO getResourceByPath(ResourceParam param) throws Exception;

    /**
     * Update resource.
     *
     * @param currentUser   current user
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0122 Cannot find any resource by id param.
     */
    ResourceVO update(ResourceParam param, User currentUser) throws Exception;

    /**
     * Delete resource.
     *
     * @param currentUser   current user
     * @param param         resource's params.
     * @throws CommonsException        SYS0122 Cannot find any resource by id param.
     */
    void delete(ResourceParam param, User currentUser) throws Exception;

}
