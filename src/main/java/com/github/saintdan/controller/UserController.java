package com.github.saintdan.controller;

import com.github.saintdan.po.User;
import com.github.saintdan.repo.UserRepository;
import com.github.saintdan.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller.
 * see {@link UserRepository}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    @RequestMapping(value = "/{usr}", method = RequestMethod.GET)
    public UserVO getUserByUsr(@PathVariable String usr) {

        User user = userRepository.findByUsr(usr);
        UserVO vo = new UserVO();
        vo.setName(user.getName());
        vo.setUsername(user.getUsr());

        return vo;
    }

}
