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
public class GroupsVO extends ResultVO implements Serializable {

    private static final long serialVersionUID = -4426455759935056168L;

    private List<GroupVO> groupVOList;

    public List<GroupVO> getGroupVOList() {
        return groupVOList;
    }

    public void setGroupVOList(List<GroupVO> groupVOList) {
        this.groupVOList = groupVOList;
    }
}
