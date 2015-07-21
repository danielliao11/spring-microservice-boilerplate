package com.github.saintdan.controller;

import com.github.saintdan.bo.UserParam;
import com.github.saintdan.constant.Resource;
import com.github.saintdan.exception.UserException;
import com.github.saintdan.po.User;
import com.github.saintdan.repo.UserRepository;
import com.github.saintdan.service.UserService;
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
@RequestMapping(Resource.RESOURCES)
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = Resource.USERS +"/{usr}", method = RequestMethod.GET)
    public UserVO getUserByUsr(@PathVariable String usr) {
        User user = null;
        try {
            user = userService.getUserByUsr(new UserParam(usr));
        } catch (UserException e) {
            e.printStackTrace();
        }
        UserVO vo = new UserVO();
        vo.setName(user.getName());
        vo.setUsername(user.getUsr());

        return vo;
    }

}
