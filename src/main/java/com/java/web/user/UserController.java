package com.java.web.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.util.HttpUtil;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserServiceInterface USI;
	
	@RequestMapping(value = "/sign_up", method = RequestMethod.POST)
	public ModelAndView sign_up(HttpServletRequest req) {
		logger.info("***************************************************");
		logger.info("@ CONTROLLER LOG : URL = /sign_up, METHOD = POST");
		return HttpUtil.makeJsonView(USI.sign_up(req));
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String test() {
		return "/";
	}
	
}
