package com.java.web.hadoop;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.util.HttpUtil;

@Controller
public class HadoopController {
	
	@Autowired
	HadoopServiceInterface HSI;
	
	@RequestMapping(value="/hadoop", method = RequestMethod.POST)
	public ModelAndView hadoop(HttpServletRequest req) throws Exception {
		return HttpUtil.makeJsonView( HSI.hadoop(req) );
	}
	
	@RequestMapping(value="/callData", method = RequestMethod.POST)
	public ModelAndView callData(HttpServletRequest req) throws Exception {
		return HttpUtil.makeJsonView( HSI.callData(req) );
	}
	
}
