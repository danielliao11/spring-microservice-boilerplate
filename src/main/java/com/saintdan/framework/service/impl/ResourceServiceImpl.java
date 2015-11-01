package com.saintdan.framework.service.impl;

import com.saintdan.framework.component.ResultHelper;
import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.constant.ResourceConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.GroupException;
import com.saintdan.framework.exception.ResourceException;
import com.saintdan.framework.param.LogParam;
import com.saintdan.framework.param.ResourceParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.ResourceRepository;
import com.saintdan.framework.service.GroupService;
import com.saintdan.framework.service.LogService;
import com.saintdan.framework.service.ResourceService;
import com.saintdan.framework.tools.SpringSecurityUtils;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.ResourceVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implements the
 * {@link ResourceService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new resource.
     *
     * @param currentUser   current user
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0031 Resource already existing, name taken.
     * @throws GroupException           GRP0012 Cannot find any group by this id param.
     */
    @Override
    public ResourceVO create(ResourceParam param, User currentUser) throws ResourceException, GroupException {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource != null) {
            // Throw resource already existing, name taken.
            throw new ResourceException(ErrorType.RSC0031);
        }
        return resourcePO2VO(resourceRepository.save(resourceParam2PO(param, new Resource(), currentUser)),
                String.format(ControllerConstant.CREATE, RESOURCE));
    }

    /**
     * Show all resources' VO.
     *
     * @return              resources' VO
     * @throws ResourceException        RSC0011 No resource exist.
     */
    @Override
    public ObjectsVO getAllResources() throws ResourceException {
        List<Resource> resources = (List<Resource>) resourceRepository.findAll();
        if (resources.isEmpty()) {
            // Throw no resource exist exception.
            throw new ResourceException(ErrorType.RSC0011);
        }
        return resourcesPO2VO(resources, String.format(ControllerConstant.INDEX, RESOURCE));
    }

    /**
     * Show resources' page VO.
     *
     * @param pageable      page
     * @return              resources' page VO
     * @throws ResourceException        RSC0011 No resource exist.
     */
    @Override
    public PageVO getPage(Pageable pageable) throws ResourceException {
        Page<Resource> resourcePage = resourceRepository.findAll(pageable);
        if (resourcePage.getContent().isEmpty()) {
            // Throw no resource exist exception.
            throw new ResourceException(ErrorType.RSC0011);
        }
        return transformer.poPage2VO(
                poList2VOList(resourcePage.getContent()),
                pageable, resourcePage.getTotalElements(),
                String.format(ControllerConstant.INDEX, RESOURCE));
    }

    /**
     * Show resources by ids.
     *
     * @param ids           resources' ids
     * @return              resources' PO
     * @throws ResourceException        RSC0012 Cannot find any resource by this id param.
     */
    @Override
    public Iterable<Resource> getResourcesByIds(Iterable<Long> ids) throws ResourceException {
        return resourceRepository.findAll(ids);
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
            // Throw resource cannot find by id parameter exception.
            throw new ResourceException(ErrorType.RSC0012);
        }
        return resourcePO2VO(resource, String.format(ControllerConstant.SHOW, RESOURCE));
    }

    /**
     * Show resource's VO by resource's name.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0013 Cannot find any resource by this name param.
     */
    @Override
    public ResourceVO getResourceByName(ResourceParam param) throws ResourceException {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource == null) {
            // Throw resource cannot find by name parameter exception.
            throw new ResourceException(ErrorType.RSC0013);
        }
        return resourcePO2VO(resource, String.format(ControllerConstant.SHOW, RESOURCE));
    }

    /**
     * Show resource's VO by resource's name.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0013 Cannot find any resource by this name param.
     */
    @Override
    public ResourceVO getResourceByPath(ResourceParam param) throws ResourceException {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource == null) {
            // Throw resource cannot find by name parameter exception.
            throw new ResourceException(ErrorType.RSC0013);
        }
        return resourcePO2VO(resource, String.format(ControllerConstant.SHOW, RESOURCE));
    }

    /**
     * Update resource.
     *
     * @param currentUser   current user
     * @param param         resource's params
     * @return              resource's VO
     * @throws ResourceException        RSC0012 Cannot find any resource by this id param.
     * @throws GroupException           GRP0012 Cannot find any group by this id param.
     */
    @Override
    public ResourceVO update(ResourceParam param, User currentUser) throws ResourceException, GroupException {
        Resource resource = resourceRepository.findOne(param.getId());
        if (resource == null) {
            // Throw resource cannot find by id parameter exception.
            throw new ResourceException(ErrorType.RSC0012);
        }
        return resourcePO2VO(resourceRepository.save(resourceParam2PO(param, resource, currentUser)),
                String.format(ControllerConstant.UPDATE, RESOURCE));
    }

    /**
     * Delete resource.
     *
     * @param currentUser   current user
     * @param param         resource's params.
     * @throws ResourceException        RSC0012 Cannot find any resource by this id param.
     */
    @Override
    public void delete(ResourceParam param, User currentUser) throws ResourceException {
        Resource resource = resourceRepository.findOne(param.getId());
        if (resource == null) {
            // Throw resource cannot find by id parameter exception.
            throw new ResourceException(ErrorType.RSC0012);
        }
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        String clientId = SpringSecurityUtils.getCurrentUsername();
        logService.create(new LogParam(ip, LogType.DELETE, clientId, ResourceConstant.RESOURCE), currentUser);
        resourceRepository.updateValidFlagFor(ValidFlag.INVALID, resource.getId());
    }


    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private GroupService groupService;

    @Autowired
    private LogService logService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResultHelper resultHelper;

    @Autowired
    private Transformer transformer;

    private final static String RESOURCE = "resource";

    /**
     * Transform resource's param to PO.
     *
     * @param param         resource's param
     * @param resource      resource
     * @param currentUser   currentUser
     * @return              resource's PO
     * @throws GroupException       GRP0012 Cannot find any group by this id param.
     */
    private Resource resourceParam2PO(ResourceParam param, Resource resource, User currentUser) throws GroupException {
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        String clientId = SpringSecurityUtils.getCurrentUsername();
        // Init createdBy, lastModifiedBy
        Long createdBy, lastModifiedBy;
        // Init createdDate
        Date createdDate = new Date();
        if (resource.getId() == null) {
            createdBy = currentUser.getId();
            lastModifiedBy = createdBy;
            logService.create(new LogParam(ip, LogType.CREATE, clientId, ResourceConstant.RESOURCE), currentUser);
        } else {
            createdBy = resource.getCreatedBy();
            createdDate = resource.getCreatedDate();
            lastModifiedBy = currentUser.getId();
            logService.create(new LogParam(ip, LogType.UPDATE, clientId, ResourceConstant.RESOURCE), currentUser);
        }
        BeanUtils.copyProperties(param, resource);
        resource.setCreatedBy(createdBy);
        resource.setCreatedDate(createdDate);
        resource.setLastModifiedBy(lastModifiedBy);
        if (!StringUtils.isBlank(param.getGroupIds())) {
            Iterable<Group> groups = groupService.getGroupsByIds(transformer.idsStr2Iterable(param.getGroupIds()));
            resource.setGroups(transformer.iterable2Set(groups));
        }
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
    private ObjectsVO resourcesPO2VO(Iterable<Resource> resources, String msg) {
        List objList = poList2VOList(resources);
        ObjectsVO vos = transformer.voList2ObjectsVO(objList, msg);
        return (ObjectsVO) resultHelper.sucessResp(vos);
    }

    /**
     * Transform resource's PO list to VO list.
     *
     * @param resources     resource's PO list
     * @return              resource's VO list
     */
    private List<ResourceVO> poList2VOList(Iterable<Resource> resources) {
        List<ResourceVO> resourceVOList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceVO vo = resourcePO2VO(resource, "");
            resourceVOList.add(vo);
        }
        return resourceVOList;
    }
}
