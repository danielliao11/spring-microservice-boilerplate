package com.saintdan.framework.controller;

import com.saintdan.framework.tools.ResponseHelper;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/2/26
 * @since JDK1.8
 */
public class CRUDController<Domain, Entity, PK> extends QueryController {

  public CRUDController() {
  }

  @PostMapping
  @ApiOperation(value = "Create new object")
  @SuppressWarnings("unchecked")
  public ResponseEntity create(@RequestBody Entity entity) {
    int result = this.domain.create(entity);
    if (result < 0) {
      return ResponseHelper.serverError("Create object failed.");
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(entity);
  }

  @PutMapping("{id}")
  @ApiOperation(value = "Update object by ID")
  @SuppressWarnings("unchecked")
  public ResponseEntity update(@RequestBody Entity entity, @PathVariable PK id) {
    try {
      BeanUtils.setProperty(entity, "id", id);
    } catch (IllegalAccessException | InvocationTargetException e) {
      return ResponseHelper.serverError("Set ID field failed.");
    }
    int result = this.domain.updateSelective(entity);
    if (result < 0) {
      return ResponseHelper.serverError("Update object failed.");
    }
    return ResponseEntity.ok(entity);
  }

  @DeleteMapping("{id}")
  @ApiOperation(value = "Delete object by ID")
  @SuppressWarnings("unchecked")
  public ResponseEntity delete(@PathVariable PK id) {
    int result = this.domain.delete(id);
    if (result < 0) {
      return ResponseHelper.serverError("Delete object failed.");
    }
    return ResponseEntity.ok().build();
  }
}
