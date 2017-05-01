package com.saintdan.framework.domain;

import com.saintdan.framework.component.Transformer;
import com.saintdan.framework.param.LogParam;
import com.saintdan.framework.po.Log;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.LogRepository;
import com.saintdan.framework.vo.LogVO;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link Log}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
@Service @Transactional(readOnly = true) public class LogDomain {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Transactional public LogVO create(LogParam param, User currentUser) throws Exception {
    return transformer.po2VO(LogVO.class, logRepository.save(logParam2PO(param, currentUser)));
  }

  @SuppressWarnings("unchecked")
  public List<LogVO> getAllLogs() throws Exception {
    List<Log> logs = logRepository.findAll();
    if (logs.isEmpty()) {
      return null;
    }
    return transformer.pos2VOs(LogVO.class, logs);
  }

  public Page getPage(Pageable pageable) throws Exception {
    Page<Log> logPage = logRepository.findAll(pageable);
    if (!logPage.hasContent()) {
      return null;
    }
    return transformer.poPage2VO(transformer.pos2VOs(LogVO.class, logPage.getContent()), pageable, logPage.getTotalElements());
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  @Autowired private Transformer transformer;

  @Autowired private LogRepository logRepository;

  private final static String LOG = "log";

  private Log logParam2PO(LogParam param, User currentUser) {
    Log log = new Log();
    BeanUtils.copyProperties(param, log);
    log.setUserId(currentUser.getId());
    log.setUsername(currentUser.getUsr());
    return log;
  }
}
