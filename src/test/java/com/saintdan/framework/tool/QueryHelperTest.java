package com.saintdan.framework.tool;

import com.saintdan.framework.tools.QueryHelper;
import org.junit.Test;
import org.springframework.data.domain.Sort;

/**
 * Test case for {@link com.saintdan.framework.tools.QueryHelper}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 7/5/16
 * @since JDK1.8
 */
public class QueryHelperTest {

  @Test
  public void testGetSort() throws Exception {
    Sort sort = QueryHelper.getSort(sortBy);
    System.out.println(sort);
  }

  private static final String sortBy = "id:asc,name:desc,date:desc";
}
