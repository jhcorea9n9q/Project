package com.java.web.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.util.HttpUtil;

@Controller
public class UserController {
	
	@Autowired
	UserServiceInterface USI;
	
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public ModelAndView idCheck(HttpServletRequest req) {
		return HttpUtil.makeJsonView(USI.idCheck(req));
	}
	
	@RequestMapping(value = "/sign_up", method = RequestMethod.POST)
	public ModelAndView sign_up(HttpServletRequest req) {
		return HttpUtil.makeJsonView(USI.sign_up(req));
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String test() {
		return "/";
	}
	
}
