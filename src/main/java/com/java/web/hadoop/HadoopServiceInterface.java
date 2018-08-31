package com.java.web.hadoop;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public interface HadoopServiceInterface {
	
	public HashMap<String, Object> hadoop(HttpServletRequest req) throws Exception;
	
	public HashMap<String, Object> callData(HttpServletRequest req)  throws Exception;
}
