package com.saintdan.framework.controller.open;

import com.saintdan.framework.domain.AccountDomain;
import com.saintdan.framework.po.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping
  public Account create(@RequestBody Account account) {
    accountDomain.create(account);
    return account;
  }

  @GetMapping
  public List<Account> all() {
    return accountDomain.findAll();
  }

  @PutMapping("{id}")
  public Account update(@RequestBody Account account) {
    accountDomain.update(account);
    return account;
  }

  private final AccountDomain accountDomain;

  @Autowired
  public TestController(AccountDomain accountDomain) {
    this.accountDomain = accountDomain;
  }
}
