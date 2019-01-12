package com.saintdan.framework.tool;

import com.saintdan.framework.tools.QueryHelper;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

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
    //given
    String sortBy = "id:asc,name:desc,date:desc";

    //when
    Sort sort = QueryHelper.getSort(sortBy);

    //then
    assertThat(sort.getOrderFor("id").isAscending()).isTrue();
    assertThat(sort.getOrderFor("name").isDescending()).isTrue();
    assertThat(sort.getOrderFor("date").isDescending()).isTrue();
  }
}
