package com.saintdan.framework.vo;

import org.springframework.data.domain.Page;

/**
 * Page's VO.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public class PageVO extends ResultVO {

    private static final long serialVersionUID = 2808529039303273022L;

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
