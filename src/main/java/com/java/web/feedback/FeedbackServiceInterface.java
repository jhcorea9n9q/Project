package com.java.web.feedback;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public interface FeedbackServiceInterface {
	
	public HashMap<String, Object> getFB(HttpServletRequest req);
	
	public HashMap<String, Object> FBList(HttpServletRequest req);
	
	public HashMap<String, Object> FBCheck(HttpServletRequest req);
	
	public HashMap<String, Object> FBCount();
	
}
