package com.saintdan.framework.tool;

import com.saintdan.framework.component.CustomPasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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


  @Test
  public void testMatches() throws Exception {
    //given
    String rawPassword = "admin";
    String encodedPassword = passwordEncoder.encode(rawPassword);

    //when
    boolean isPasswordMatched = passwordEncoder.matches(rawPassword, encodedPassword);

    //then
    assertThat(rawPassword).isNotEqualTo(encodedPassword);
    assertThat(isPasswordMatched).isTrue();
  }
}
