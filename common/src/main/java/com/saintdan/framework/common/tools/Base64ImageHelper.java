package com.saintdan.framework.common.tools;


import com.saintdan.framework.common.constant.CommonsConstant;
import com.saintdan.framework.common.exception.IllegalExtTypeException;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * decode base64 image utilities.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/10/15
 * @since JDK1.8
 */
public class Base64ImageHelper {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Transform Mr.Base64Image to Headless Knight.
   *
   * @param image Mr.Base64Image
   * @return Headless Knight
   * @throws IllegalExtTypeException not in the list
   */
  public static byte[] transformToHeadlessKnight(String image) throws IllegalExtTypeException {
    return Base64.decodeBase64(cutHead(image));
  }

  private final static String HAIR = "data:image/";
  private final static String NECK = ";base64,";
  private final static String PNG = "png";
  private final static String JPEG = "jpeg";
  private final static String JPG = "jpg";
  private final static String GIF = "gif";

  // ------------------------
  // PRIVATE METHODS
  // ------------------------

  /**
   * Cut head!
   * Mr.Base64Image to Mr.headless.
   *
   * @param image Mr.Base64Image
   * @return Mr.Headless
   * @throws IllegalExtTypeException not in the list
   */
  private static String cutHead(String image) throws IllegalExtTypeException {
    return image.replace(formHead(HAIR, cutWhom(image), NECK), "");
  }

  /**
   * I'll Form the Head!
   *
   * @param hair hair
   * @param face face
   * @param neck neck
   * @return head
   */
  private static String formHead(String hair, String face, String neck) {
    return String.join(CommonsConstant.BLANK, hair, face, neck);
  }

  /**
   * Who‘s the poor guy, let me check the list.
   *
   * @param image luckless guy
   * @return name of luckless guy
   * @throws IllegalExtTypeException not in the list
   */
  private static String cutWhom(String image) throws IllegalExtTypeException {
    if (image.contains(PNG)) {
      return PNG;
    } else if (image.contains(JPEG)) {
      return JPEG;
    } else if (image.contains(JPG)) {
      return JPG;
    } else if (image.contains(GIF)) {
      return GIF;
    } else {
      throw new IllegalExtTypeException("Illegal picture ext.");
    }
  }

}
