package com.java.web.board;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public interface BoardServiceInterface {
	
	public HashMap<String, Object> boardList(HttpServletRequest req);
	
}
