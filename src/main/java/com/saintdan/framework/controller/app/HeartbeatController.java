package com.saintdan.framework.controller.app;

import com.saintdan.framework.constant.ResourceURL;
import com.saintdan.framework.constant.VersionConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Heartbeat for app.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/02/2017
 * @since JDK1.8
 */
@Api(value = "Heartbeat") @RestController @RequestMapping(ResourceURL.RESOURCES + VersionConstant.V1 + ResourceURL.APP + ResourceURL.HEARTBEAT)
public class HeartbeatController {

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Heartbeat", httpMethod = "GET", response = ResponseEntity.class)
  @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  public ResponseEntity heartbeat() {
    return new ResponseEntity(HttpStatus.OK);
  }
}
