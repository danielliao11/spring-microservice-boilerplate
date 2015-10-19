package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.ResourceException;
import com.saintdan.framework.param.ResourceParam;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.repo.ResourceRepository;
import com.saintdan.framework.service.ResourceService;
import com.saintdan.framework.vo.ResourceVO;
import com.saintdan.framework.vo.ResourcesVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the
 * {@link ResourceService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
public class ResourceServiceImpl implements ResourceService {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new resource.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0031 Resource already existing, name taken.
     */
    @Override
    public ResourceVO create(ResourceParam param) throws ResourceException {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource != null) {
            // Throw Resource already existing, name taken.
            throw new ResourceException(ErrorType.GRP0031);
        }
        return resourcePO2VO(resourceRepository.save(resourceParam2PO(param)),
                String.format(ControllerConstant.CREATE, RESOURCE));
    }

    /**
     * Show all resources' VO.
     *
     * @return              resources' VO
     * @throws ResourceException        RSC0011 No resource exist.
     */
    @Override
    public ResourcesVO getAllResources() throws ResourceException {
        List<Resource> resources = (List<Resource>) resourceRepository.findAll();
        if (resources.isEmpty()) {
            // Throw no Resource exist exception.
            throw new ResourceException(ErrorType.GRP0011);
        }
        return resourcesPO2VO(resources, String.format(ControllerConstant.INDEX, RESOURCE));
    }

    /**
     * Show resource's VO by resource's id.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0012 Cannot find any resource by this id param.
     */
    @Override
    public ResourceVO getResourceById(ResourceParam param) throws ResourceException {
        Resource resource = resourceRepository.findOne(param.getId());
        if (resource == null) {
            // Throw Resource cannot find by id parameter exception.
            throw new ResourceException(ErrorType.GRP0012);
        }
        return resourcePO2VO(resource, String.format(ControllerConstant.SHOW, RESOURCE));
    }

    /**
     * Show resource's VO by resource's name.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0011 Cannot find any resource by this name param.
     */
    @Override
    public ResourceVO getResourceByName(ResourceParam param) throws ResourceException {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource == null) {
            // Throw Resource cannot find by name parameter exception.
            throw new ResourceException(ErrorType.GRP0013);
        }
        return resourcePO2VO(resource, String.format(ControllerConstant.SHOW, RESOURCE));
    }

    /**
     * Show resource's VO by resource's name.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0011 Cannot find any resource by this name param.
     */
    @Override
    public ResourceVO getResourceByPath(ResourceParam param) throws ResourceException {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource == null) {
            // Throw Resource cannot find by name parameter exception.
            throw new ResourceException(ErrorType.GRP0013);
        }
        return resourcePO2VO(resource, String.format(ControllerConstant.SHOW, RESOURCE));
    }

    /**
     * Update resource.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0012 Cannot find any resource by this id param.
     */
    @Override
    public ResourceVO update(ResourceParam param) throws ResourceException {
        Resource resource = resourceRepository.findOne(param.getId());
        if (resource == null) {
            // Throw Resource cannot find by id parameter exception.
            throw new ResourceException(ErrorType.GRP0012);
        }
        return resourcePO2VO(resourceRepository.save(resourceParam2PO(param)),
                String.format(ControllerConstant.UPDATE, RESOURCE));
    }

    /**
     * Delete resource.
     *
     * @param param         resource's params.
     * @throws ResourceException        RSC0012 Cannot find any resource by this id param.
     */
    @Override
    public void delete(ResourceParam param) throws ResourceException {
        Resource resource = resourceRepository.findOne(param.getId());
        if (resource == null) {
            // Throw role cannot find by id parameter exception.
            throw new ResourceException(ErrorType.ROL0012);
        }
        resourceRepository.delete(resource);
    }


    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResultHelper resultHelper;

    private final static String RESOURCE = "resource";

    /**
     * Transform resource's param to PO.
     *
     * @param param         resource's param
     * @return              resource's PO
     */
    private Resource resourceParam2PO(ResourceParam param) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(param, resource);
        return resource;
    }

    /**
     * Transform resource's PO to VO.
     *
     * @param resource      resource's PO
     * @param msg           return message
     * @return              resource's VO
     */
    private ResourceVO resourcePO2VO(Resource resource, String msg) {
        ResourceVO vo = new ResourceVO();
        BeanUtils.copyProperties(resource, vo);
        if (StringUtils.isBlank(msg)) {
            return vo;
        }
        vo.setMessage(msg);
        // Return success result.
        return (ResourceVO) resultHelper.sucessResp(vo);
    }

    /**
     * Transform resources' PO to resources VO
     *
     * @param resources     resources' PO
     * @param msg           return message
     * @return              resources' VO
     */
    private ResourcesVO resourcesPO2VO(Iterable<Resource> resources, String msg) {
        ResourcesVO vos = new ResourcesVO();
        List<ResourceVO> resourceVOList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceVO vo = resourcePO2VO(resource, "");
            resourceVOList.add(vo);
        }
        vos.setResourceVOList(resourceVOList);
        if (StringUtils.isBlank(msg)) {
            return vos;
        }
        vos.setMessage(msg);
        return (ResourcesVO) resultHelper.sucessResp(vos);
    }
}
