package com.java.web.feedback;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.util.HttpUtil;

@Controller
public class FeedbackController {
	
	@Autowired
	FeedbackServiceInterface FSI;
	
	@RequestMapping(value="/getFB", method = RequestMethod.POST)
	public ModelAndView getFB(HttpServletRequest req) {
		return HttpUtil.makeJsonView( FSI.getFB(req) );
	}
	
	@RequestMapping(value="/FBList", method = RequestMethod.POST)
	public ModelAndView FBList(HttpServletRequest req) {
		return HttpUtil.makeJsonView( FSI.FBList(req) );
	}
	
	@RequestMapping(value="/FBCheck", method = RequestMethod.POST)
	public ModelAndView FBCheck(HttpServletRequest req) {
		return HttpUtil.makeJsonView( FSI.FBCheck(req) );
	}
	
	@RequestMapping(value = "/FBCount", method = RequestMethod.POST)
	public ModelAndView FBCount() {
		return HttpUtil.makeJsonView( FSI.FBCount() );
	}

}
