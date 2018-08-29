package com.java.web.feedback;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.dao.DaoInterface;
import com.java.web.util.HttpUtil;

@Service
public class FeedbackService implements FeedbackServiceInterface {
	
	@Autowired
	DaoInterface DI;

	@Override
	public HashMap<String, Object> getFB(HttpServletRequest req) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("sqlType", "feedback.getFeedback");
		param.put("sql", "insert");
		int status = (int) DI.callDB(param);
		if(status==0) {
			resultMap.put("status", 0);
		}else if(status==1) {
			resultMap.put("status", 1);
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> FBList(HttpServletRequest req) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("limit", Integer.parseInt(param.get("limit").toString()) );
		param.put("sql", "selectList");
		param.put("sqlType", "feedback.feedbackList");
		List<HashMap<String, Object>> feedbackList = (List<HashMap<String, Object>>) DI.callDB(param);
		for(int f=0; f<feedbackList.size(); f++) {
			feedbackList.get(f).put("fdDate", new SimpleDateFormat("yyyy MM dd HH mm ss").format(feedbackList.get(f).get("fdDate")) );
		}
		resultMap.put("list", feedbackList);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> FBCheck(HttpServletRequest req) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("sql", "update");
		param.put("sqlType", "feedback.feedbackCheck");
		int status = (int) DI.callDB(param);
		if(status == 0) {
			resultMap.put("status", 0);
		}else if(status == 1) {
			resultMap.put("status", 1);
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> FBCount() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sql", "selectOne");
		param.put("sqlType", "feedback.feedbackCount");
		int FBCount = (int) DI.callDB(param);
		resultMap.put("count", FBCount);
		return resultMap;
	}

}
