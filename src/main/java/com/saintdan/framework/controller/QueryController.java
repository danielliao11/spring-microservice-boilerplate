package com.saintdan.framework.controller;

import com.saintdan.framework.domain.BaseDomain;
import com.saintdan.framework.param.BaseParam;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
public class QueryController<Domain extends BaseDomain, Param extends BaseParam, Entity, PK> {

  @GetMapping
  @ApiOperation(value = "Get all objects")
  public ResponseEntity all() {
    return ResponseEntity.ok(this.domain.findAll());
  }

  @GetMapping("page")
  @ApiOperation(value = "Get all objects")
  @SuppressWarnings("unchecked")
  public ResponseEntity page(Param param) {
    return ResponseEntity.ok(domain.page(param));
  }

  @GetMapping("{id}")
  public ResponseEntity detail(@PathVariable PK id) {
    return ResponseEntity.ok(domain.findById(id));
  }

  @Autowired protected HttpServletRequest request;
  @Autowired protected Domain domain;
}
