package com.java.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	@RequestMapping(value = "/sign_up", method = RequestMethod.POST)
	public ModelAndView sign_up(HttpServletRequest req) {
		return HttpUtil.makeJsonView( USI.sign_up(req) );
	}
	
	@RequestMapping(value = "/tryLogin", method = RequestMethod.POST)
	public ModelAndView tryLogin(HttpServletRequest req, HttpSession sess) {
		return HttpUtil.makeJsonView( USI.tryLogin(req, sess) );
	}
	
	@RequestMapping(value = "/sessionCheck", method = RequestMethod.POST)
	public ModelAndView sessionCheck(HttpSession sess) {
		return HttpUtil.makeJsonView(USI.sessionCheck(sess));
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession sess) {
		sess.invalidate();
		return "redirect:/page/main.html";
	}
	
	@RequestMapping(value = "/chID", method = RequestMethod.POST)
	public ModelAndView chID(HttpServletRequest req, HttpSession sess) {
		return HttpUtil.makeJsonView( USI.chID(req, sess) );
	}
	
	@RequestMapping(value = "/chPWD", method = RequestMethod.POST)
	public ModelAndView chPWD(HttpServletRequest req, HttpSession sess) {
		return HttpUtil.makeJsonView( USI.chPWD(req, sess) );
	}
	
	@RequestMapping(value = "/chGetMail", method = RequestMethod.POST)
	public ModelAndView chGetMail(HttpServletRequest req, HttpSession sess) {
		return HttpUtil.makeJsonView( USI.chGetMail(req, sess) );
	}
	
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	public ModelAndView delUser(HttpSession sess) {
		return HttpUtil.makeJsonView( USI.delUser(sess) );
	}
	
	@RequestMapping(value = "/userList", method = RequestMethod.POST)
	public ModelAndView userList(HttpServletRequest req) {
		return HttpUtil.makeJsonView( USI.userList(req) );
	}
	
	@RequestMapping(value = "/userCount", method = RequestMethod.POST)
	public ModelAndView userCount() {
		return HttpUtil.makeJsonView( USI.userCount() );
	}
	
	@RequestMapping(value = "/adminDelUser", method = RequestMethod.POST)
	public ModelAndView adminDelUser(HttpServletRequest req) {
		return HttpUtil.makeJsonView( USI.adminDelUser(req) );
	}
}