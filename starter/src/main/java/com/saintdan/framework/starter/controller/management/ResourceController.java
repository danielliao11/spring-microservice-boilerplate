package com.saintdan.framework.starter.controller.management;

import com.saintdan.framework.common.constant.ResourcePath;
import com.saintdan.framework.common.controller.CRUDController;
import com.saintdan.framework.starter.po.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Api for {@link Resource}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/3/31
 * @since JDK1.8
 */
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.MANAGEMENT + ResourcePath.RESOURCES)
public class ResourceController extends CRUDController<Resource, String> {
}
