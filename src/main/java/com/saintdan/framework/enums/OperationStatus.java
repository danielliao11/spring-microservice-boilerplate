package com.saintdan.framework.enums;

/**
 * Operation status.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public enum OperationStatus implements IntentStateWithDescription {

  SUCCESS("Operate success"),
  FAILURE("Operate failed"),
  TIMEOUT("Time out"),;

  private final String description;

  OperationStatus(String description) {
    this.description = description;
  }

  @Override public String description() {
    return this.description;
  }

}
