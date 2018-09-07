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

	@Override
	public HashMap<String, Object> boardIn(HttpServletRequest req) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("sql", "selectOne");
		param.put("sqlType", "board.movieCheck");
		HashMap<String, Object> movieData = (HashMap<String, Object>) DI.callDB(param);
		if(movieData==null) {
			param.put("sql", "insert");
			param.put("sqlType", "board.boardIn2");
			int status2 = (int) DI.callDB(param);
			if(status2!=0) {
				param.put("sql", "selectOne");
				param.put("sqlType", "board.movieCheck");
				HashMap<String, Object> movieData2 = (HashMap<String, Object>) DI.callDB(param);
				if(movieData2!=null) {
					param.put("movieNo", (int) movieData2.get("movieNo"));
					param.put("sql", "insert");
					param.put("sqlType", "board.boardIn");
					int status1 = (int) DI.callDB(param);
					if(status1!=0) {
						param.put("sql", "update");
						param.put("sqlType", "user.revCount");
						int status3 = (int) DI.callDB(param);
						if(status3 != 0 ) {
							resultMap.put("status", 1);
						}else {
							resultMap.put("status", 0);
						}
					}else {
						resultMap.put("status", 0);
					}
				}else {
					resultMap.put("status", 0);
				}
			}else {
				resultMap.put("status", 0);
			}
		}else {
			param.put("movieNo", (int) movieData.get("movieNo"));
			param.put("sql", "insert");
			param.put("sqlType", "board.boardIn");
			int status1 = (int) DI.callDB(param);
			if(status1!=0) {
				param.put("sql", "update");
				param.put("sqlType", "user.revCount");
				int status3 = (int) DI.callDB(param);
				if(status3 != 0 ) {
					resultMap.put("status", 1);
				}else {
					resultMap.put("status", 0);
				}
			}else {
				resultMap.put("status", 0);
			}
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> boardDetail(int boardNo) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sql", "selectOne");
		param.put("sqlType", "board.boardDetail");
		param.put("boardNo", boardNo);
		HashMap<String, Object> boardData = (HashMap<String, Object>) DI.callDB(param);
		param.put("sql", "update");
		param.put("sqlType", "board.visitCount");
		int status = (int) DI.callDB(param);
		boardData.put("revDate", new SimpleDateFormat("yyyy MM dd HH mm ss").format(boardData.get("revDate")) );
		resultMap.put("status", status);
		resultMap.put("result", boardData);
		return resultMap;
	}

}
