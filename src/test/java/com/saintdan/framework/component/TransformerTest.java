package com.saintdan.framework.component;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test case for {@link Transformer}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/16
 * @since JDK1.8
 */
public class TransformerTest {

  @Test
  public void TestIdsStr2Iterable() throws Exception {
    final String ids = "1,2,3,4,5";
    List<Long> expected = new ArrayList<>();
    expected.add(1L);
    expected.add(2L);
    expected.add(3L);
    expected.add(4L);
    expected.add(5L);
    List<Long> actual = Lists.newArrayList(transformer.idsStr2List(ids));
    actual.forEach(System.out::println);
    Assert.assertEquals(expected, actual);
  }

  private Transformer transformer = new Transformer();

}
