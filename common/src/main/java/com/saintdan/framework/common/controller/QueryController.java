package com.saintdan.framework.common.controller;

import com.saintdan.framework.common.domain.BaseDomain;
import com.saintdan.framework.common.param.BaseParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * API of query
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/2/26
 * @since JDK1.8
 */
public class QueryController<Domain extends BaseDomain, Param extends BaseParam, PK> {

  @GetMapping
  @ApiOperation(value = "Get all objects")
  public ResponseEntity all() {
    return ResponseEntity.ok(this.domain.findAll());
  }

  @GetMapping("page")
  @ApiOperation(value = "Get all objects")
  public ResponseEntity page(Param param) {
    return ResponseEntity.ok(this.domain.page(param));
  }

  @GetMapping("{id}")
  public ResponseEntity detail(@PathVariable PK id) {
    return ResponseEntity.ok(this.domain.findById(id));
  }

  protected Domain domain;

  public void setDomain(Domain domain) {
    this.domain = domain;
  }
}
