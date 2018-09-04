package com.java.web.board;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.dao.DaoInterface;
import com.java.web.util.HttpUtil;

@Service
public class BoardService implements BoardServiceInterface {
	
	@Autowired
	DaoInterface DI;

	@Override
	public HashMap<String, Object> boardList(HttpServletRequest req) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("limit", Integer.parseInt(param.get("limit").toString()) );
		param.put("sql", "selectList");
		param.put("sqlType", "board.boardList");
		List<HashMap<String, Object>> boardList = (List<HashMap<String, Object>>) DI.callDB(param);
		for(int b=0; b<boardList.size(); b++) {
			boardList.get(b).put("revDate", new SimpleDateFormat("yyyy MM dd HH mm ss").format(boardList.get(b).get("revDate")) );
		}
		resultMap.put("list", boardList);
		return resultMap;
	}

}
