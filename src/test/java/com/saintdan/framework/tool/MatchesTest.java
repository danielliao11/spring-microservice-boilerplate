package com.saintdan.framework.tool;

import com.saintdan.framework.component.CustomPasswordEncoder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @{@link CustomPasswordEncoder}
 * @date 6/29/15
 * @since JDK1.8
 */
public class MatchesTest {

  private CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();

  private static final String RAW_PASSWORD = "admin";

  @Test
  public void testMatches() throws Exception {
    Assert.assertTrue(passwordEncoder.matches(RAW_PASSWORD, passwordEncoder.encode(RAW_PASSWORD)));
  }
}
