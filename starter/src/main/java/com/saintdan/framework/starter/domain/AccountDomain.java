package com.saintdan.framework.starter.domain;

import com.saintdan.framework.common.domain.BaseDomain;
import com.saintdan.framework.starter.mapper.AccountMapper;
import com.saintdan.framework.starter.po.Account;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 2019/1/2
 * @since JDK1.8
 */
@Service
public class AccountDomain extends BaseDomain<AccountMapper, Account> {
}
