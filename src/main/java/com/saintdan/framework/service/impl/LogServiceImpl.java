package com.saintdan.framework.service.impl;

import com.saintdan.framework.po.Log;
import com.saintdan.framework.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implements the
 * {@link LogService}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
@Service
@Transactional
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {

}
