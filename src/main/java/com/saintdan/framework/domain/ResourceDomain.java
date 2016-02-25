package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.constant.CommonsConstant;
import com.saintdan.framework.constant.ControllerConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.enums.LogType;
import com.saintdan.framework.enums.ValidFlag;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.ResourceParam;
import com.saintdan.framework.po.Group;
import com.saintdan.framework.po.Resource;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.ResourceRepository;
import com.saintdan.framework.tools.ErrorMsgHelper;
import com.saintdan.framework.vo.ObjectsVO;
import com.saintdan.framework.vo.PageVO;
import com.saintdan.framework.vo.ResourceVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Domain of {@link Resource}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional
public class ResourceDomain extends BaseDomain<Resource, Long> {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Create new resource.
     *
     * @param currentUser   current user
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0111 resource already existing, name taken.
     */
    public ResourceVO create(ResourceParam param, User currentUser) throws Exception {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource != null) {
            // Throw group already existing exception, name taken.
            throw new CommonsException(ErrorType.SYS0111,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.NAME));
        }
        return super.createByPO(ResourceVO.class, resourceParam2PO(param, new Resource(), currentUser), currentUser);
    }

    /**
     * Show all resources' VO.
     *
     * @return              resources' VO
     * @throws CommonsException        SYS0120 No resource exists.
     */
    public ObjectsVO getAllResources() throws Exception {
        List<Resource> resources = (List<Resource>) resourceRepository.findAll();
        if (resources.isEmpty()) {
            // Throw no resource exist exception.
            throw new CommonsException(ErrorType.SYS0120,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.pos2VO(ObjectsVO.class, resources, String.format(ControllerConstant.INDEX, getClassT()));
    }

    /**
     * Show resources' page VO.
     *
     * @param pageable      page
     * @return              resources' page VO
     * @throws CommonsException        SYS0120 No resource exists.
     */
    public PageVO getPage(Pageable pageable) throws Exception {
        Page<Resource> resourcePage = resourceRepository.findAll(pageable);
        if (resourcePage.getContent().isEmpty()) {
            // Throw no resource exist exception.
            throw new CommonsException(ErrorType.SYS0120,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0120, getClassT().getSimpleName(), getClassT().getSimpleName()));
        }
        return transformer.poPage2VO(transformer.poList2VOList(ResourceVO.class, resourcePage.getContent()), pageable, resourcePage.getTotalElements(),
                String.format(ControllerConstant.INDEX, getClassT()));
    }

    /**
     * Show resources by ids.
     *
     * @param ids           resources' ids
     * @return              resources' PO
     * @throws CommonsException        SYS0120 No resource exists.
     */
    public Iterable<Resource> getResourcesByIds(Iterable<Long> ids) throws Exception {
        return resourceRepository.findAll(ids);
    }

    /**
     * Show resource's VO by resource's id.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0122 Cannot find any resource by id param.
     */
    public ResourceVO getResourceById(ResourceParam param) throws Exception {
        Resource resource = resourceRepository.findOne(param.getId());
        if (resource == null) {
            // Throw resource cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return transformer.po2VO(ResourceVO.class, resource, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Show resource's VO by resource's name.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0122 Cannot find any resource by name param.
     */
    public ResourceVO getResourceByName(ResourceParam param) throws Exception {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource == null) {
            // Throw resource cannot find by name parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.NAME));
        }
        return transformer.po2VO(ResourceVO.class, resource, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Show resource's VO by resource's path.
     *
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0122 Cannot find any resource by path param.
     */
    public ResourceVO getResourceByPath(ResourceParam param) throws Exception {
        Resource resource = resourceRepository.findByName(param.getName());
        if (resource == null) {
            // Throw resource cannot find by name parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), PATH));
        }
        return transformer.po2VO(ResourceVO.class, resource, String.format(ControllerConstant.SHOW, getClassT()));
    }

    /**
     * Update resource.
     *
     * @param currentUser   current user
     * @param param         resource's params
     * @return              resource's VO
     * @throws CommonsException        SYS0122 Cannot find any resource by id param.
     */
    public ResourceVO update(ResourceParam param, User currentUser) throws Exception {
        Resource resource = resourceRepository.findOne(param.getId());
        if (resource == null) {
            // Throw resource cannot find by id parameter exception.
            // Throw cannot find any group by this id param.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        return super.updateByPO(ResourceVO.class, resourceParam2PO(param, new Resource(), currentUser), currentUser);
    }

    /**
     * Delete resource.
     *
     * @param currentUser   current user
     * @param param         resource's params.
     * @throws CommonsException        SYS0122 Cannot find any resource by id param.
     */
    public void delete(ResourceParam param, User currentUser) throws Exception {
        Resource resource = resourceRepository.findOne(param.getId());
        if (resource == null) {
            // Throw resource cannot find by id parameter exception.
            throw new CommonsException(ErrorType.SYS0122,
                    ErrorMsgHelper.getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), getClassT().getSimpleName(), CommonsConstant.ID));
        }
        // Log delete operation.
        logHelper.logUsersOperations(LogType.DELETE, getClassT().getSimpleName(), currentUser);
        // Change valid flag to invalid.
        resourceRepository.updateValidFlagFor(ValidFlag.INVALID, resource.getId());
    }


    // --------------------------
    // PRIVATE FIELDS AND METHODS
    // --------------------------

    @Autowired
    private GroupDomain groupService;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private Transformer transformer;

    private static final String PATH = "path";

    /**
     * Transform resource's param to PO.
     *
     * @param param         resource's param
     * @param resource      resource
     * @param currentUser   currentUser
     * @return              resource's PO
     */
    private Resource resourceParam2PO(ResourceParam param, Resource resource, User currentUser) throws Exception {
        transformer.param2PO(getClassT(), param, resource, currentUser);
        if (!StringUtils.isBlank(param.getGroupIds())) {
            Iterable<Group> groups = groupService.getGroupsByIds(transformer.idsStr2Iterable(param.getGroupIds()));
            resource.setGroups(transformer.iterable2Set(groups));
        }
        return resource;
    }

}
