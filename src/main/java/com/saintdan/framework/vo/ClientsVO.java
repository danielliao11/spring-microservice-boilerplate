package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Clients' VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/25/15
 * @since JDK1.8
 */
public class ClientsVO extends ResultVO implements Serializable {

    private static final long serialVersionUID = 4195975085393285933L;

    private List<ClientVO> clientVOList;

    public List<ClientVO> getClientVOList() {
        return clientVOList;
    }

    public void setClientVOList(List<ClientVO> clientVOList) {
        this.clientVOList = clientVOList;
    }
}
