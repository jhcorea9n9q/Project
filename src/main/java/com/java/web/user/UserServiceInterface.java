package com.java.web.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserServiceInterface {
	
	public HashMap<String, Object> sign_up(HttpServletRequest req);
	
	public HashMap<String, Object> tryLogin(HttpServletRequest req, HttpSession sess);
	
	public HashMap<String, Object> sessionCheck(HttpSession sess);
	
	public HashMap<String, Object> chID(HttpServletRequest req, HttpSession session);
	
	public HashMap<String, Object> chPWD(HttpServletRequest req, HttpSession session);
	
	public HashMap<String, Object> chGetMail(HttpServletRequest req, HttpSession session);
	
	public HashMap<String, Object> delUser(HttpSession session);
	
}
