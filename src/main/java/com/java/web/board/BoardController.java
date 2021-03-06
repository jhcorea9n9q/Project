package com.java.web.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.util.HttpUtil;

@Controller
public class BoardController {
	
	@Autowired
	BoardServiceInterface BSI;
	
	@RequestMapping(value = "/reviewBoard")
	public String reviews() {
		return "boardKR";
	}
	
	@RequestMapping(value = "/boardList", method = RequestMethod.POST)
	public ModelAndView boardList(HttpServletRequest req) {
		return HttpUtil.makeJsonView( BSI.boardList(req) );
	}
	
	@RequestMapping(value = "/boardIn", method = RequestMethod.POST)
	public ModelAndView boardIn(HttpServletRequest req) {
		return HttpUtil.makeJsonView( BSI.boardIn(req) );
	}
	
	@RequestMapping(value = "/boardDetail/{boardNo}", method = RequestMethod.POST)
	public ModelAndView boardDetail(@PathVariable("boardNo") int boardNo) {
		return HttpUtil.makeJsonView( BSI.boardDetail(boardNo) );
	}
	
}
