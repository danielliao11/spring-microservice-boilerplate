package com.saintdan.framework.component;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test case for {@link Transformer}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/16
 * @since JDK1.8
 */
public class TransformerTest {

  private Transformer transformer = new Transformer();

  @Test
  public void TestIdsStr2Iterable() throws Exception {
    //given
    final String ids = "1,2,3,4,5";
    List<Long> given = new ArrayList<>();
    given.add(1L);
    given.add(2L);
    given.add(3L);
    given.add(4L);
    given.add(5L);

    //when
    List<Long> output = Lists.newArrayList(transformer.idsStr2List(ids));

    //then
    assertThat(output).isEqualTo(given);
  }


}
