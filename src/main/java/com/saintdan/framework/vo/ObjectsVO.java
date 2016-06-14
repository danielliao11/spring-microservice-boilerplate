package com.saintdan.framework.vo;

import java.io.Serializable;
import java.util.List;

/**
 * VO for {@link List<Object>}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
public class ObjectsVO implements Serializable {

  private static final long serialVersionUID = 7822963463824140286L;

  private List objects;

  public List getObjects() {
    return objects;
  }

  public void setObjects(List objects) {
    this.objects = objects;
  }
}
