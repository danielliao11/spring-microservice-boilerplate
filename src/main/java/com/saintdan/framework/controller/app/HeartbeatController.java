package com.saintdan.framework.controller.app;

import com.saintdan.framework.constant.ResourcePath;
import com.saintdan.framework.constant.VersionConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Heartbeat")
@RestController
@RequestMapping(
    ResourcePath.RESOURCES + VersionConstant.V1 + ResourcePath.APP + ResourcePath.HEARTBEAT)
public class HeartbeatController {

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "Heartbeat", httpMethod = "GET", response = ResponseEntity.class)
  @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  public ResponseEntity heartbeat() {
    return ResponseEntity.ok().build();
  }
}
