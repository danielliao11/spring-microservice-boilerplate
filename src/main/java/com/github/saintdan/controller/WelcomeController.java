package com.github.saintdan.controller;

import com.github.saintdan.vo.WelcomeVO;
import com.github.saintdan.po.User;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Welcome controller.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@RestController
public class WelcomeController {

	private static final String template = "Hello, %s!";
	
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/welcome")
	public WelcomeVO welcome(@AuthenticationPrincipal User user) {
		return new WelcomeVO(user.getId(), String.format(template, user.getName()));
	}

}
