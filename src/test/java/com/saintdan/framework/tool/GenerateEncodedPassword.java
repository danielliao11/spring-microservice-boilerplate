package com.saintdan.framework.tool;

import com.saintdan.framework.component.CustomPasswordEncoder;
import org.apache.commons.text.RandomStringGenerator;

/**
 * Generate encode password.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/29/15
 * @since JDK1.8
 */
public class GenerateEncodedPassword {

  public static void main(String[] args) {
    RandomStringGenerator generator = new RandomStringGenerator.Builder()
        .withinRange('!', '}').build();
    String pwd = generator.generate(32);
    System.out.println(pwd);
    String encodedPassword = new CustomPasswordEncoder().encode(pwd);
    System.out.println(encodedPassword);
  }
}
