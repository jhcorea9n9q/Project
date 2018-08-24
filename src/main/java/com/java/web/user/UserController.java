package com.java.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserServiceInterface USI;
	
	@RequestMapping(value = "/sign_up", method = RequestMethod.POST)
	public ModelAndView sign_up(HttpServletRequest req) {
		logger.info("@ CONTROLLER LOG : URL = /sign_up, METHOD = POST");
		return HttpUtil.makeJsonView( USI.sign_up(req) );
	}
	
	@RequestMapping(value = "/tryLogin", method = RequestMethod.POST)
	public ModelAndView tryLogin(HttpServletRequest req, HttpSession sess) {
		logger.info("@ CONTROLLER LOG : URL = /tryLogin, METHOD = POST");
		return HttpUtil.makeJsonView( USI.tryLogin(req, sess) );
	}
	
	@RequestMapping(value = "/sessionCheck", method = RequestMethod.POST)
	public ModelAndView sessionCheck(HttpSession sess) {
		logger.info("@ CONTROLLER LOG : URL = /sessionCheck, METHOD = POST");
		return HttpUtil.makeJsonView(USI.sessionCheck(sess));
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession sess) {
		logger.info("@ CONTROLLER LOG : URL = /logout");
		sess.invalidate();
		return "redirect:/page/main.html";
	}
	
	@RequestMapping(value = "/chID", method = RequestMethod.POST)
	public ModelAndView chID(HttpServletRequest req, HttpSession sess) {
		logger.info("@ CONTROLLER LOG : URL = /chID, METHOD = POST");
		return HttpUtil.makeJsonView( USI.chID(req, sess) );
	}
	
	@RequestMapping(value = "/chPWD", method = RequestMethod.POST)
	public ModelAndView chPWD(HttpServletRequest req, HttpSession sess) {
		logger.info("@ CONTROLLER LOG : URL = /chPWD, METHOD = POST");
		return HttpUtil.makeJsonView( USI.chPWD(req, sess) );
	}
	
	@RequestMapping(value = "/chGetMail", method = RequestMethod.POST)
	public ModelAndView chGetMail(HttpServletRequest req, HttpSession sess) {
		logger.info("@ CONTROLLER LOG : URL = /chGetMail, METHOD = POST");
		return HttpUtil.makeJsonView( USI.chGetMail(req, sess) );
	}
	
	@RequestMapping("/delUser")
	public String delUser(HttpSession sess) {
		logger.info("@ CONTROLLER LOG : URL = /delUser");
		sess.invalidate();
		return "redirect:/page/main.html";
	}
	
}