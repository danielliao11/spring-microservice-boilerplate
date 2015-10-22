package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Groups' VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public class ResourcesVO extends ResultVO implements Serializable {

    private static final long serialVersionUID = -5467429419645793644L;

    private List<ResourceVO> resourceVOList;

    public List<ResourceVO> getResourceVOList() {
        return resourceVOList;
    }

    public void setResourceVOList(List<ResourceVO> resourceVOList) {
        this.resourceVOList = resourceVOList;
    }
}
