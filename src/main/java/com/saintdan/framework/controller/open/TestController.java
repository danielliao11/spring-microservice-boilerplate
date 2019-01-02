package com.saintdan.framework.controller.open;

import com.saintdan.framework.po.Account;
import com.saintdan.framework.mapper.AccountMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
@RestController
@RequestMapping("open/test")
public class TestController {

  @GetMapping
  public List<Account> all() {
    return accountMapper.selectAll();
  }

  private final AccountMapper accountMapper;

  @Autowired
  public TestController(AccountMapper accountMapper) {
    this.accountMapper = accountMapper;
  }
}
