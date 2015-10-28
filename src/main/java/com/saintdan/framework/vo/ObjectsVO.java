package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Objects' VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
public class ObjectsVO extends ResultVO implements Serializable {

    private static final long serialVersionUID = 7822963463824140286L;

    private List objectsVOList;

    public List getObjectsVOList() {
        return objectsVOList;
    }

    public void setObjectsVOList(List objectsVOList) {
        this.objectsVOList = objectsVOList;
    }
}
