package com.saintdan.framework.service;

import com.saintdan.framework.BaseTest;
import com.saintdan.framework.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public class UserTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

}
