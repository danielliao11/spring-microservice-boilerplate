package com.saintdan.framework.vo;

import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * VO for {@link Page}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public class PageVO implements Serializable {

  private static final long serialVersionUID = 2808529039303273022L;

  private Page page;

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }
}
