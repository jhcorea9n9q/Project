package com.java.web.user;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.dao.DaoInterface;
import com.java.web.util.HttpUtil;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	DaoInterface DI;

	@Override
	public HashMap<String, Object> sign_up(HttpServletRequest req) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("sqlType", "user.idCheck");
		param.put("sql", "selectOne");
		HashMap<String, Object> idCheck = (HashMap<String, Object>) DI.callDB(param);
		if(idCheck == null) {
			resultMap.put("status", 0);
		}else{
			String check = idCheck.get("check").toString();
			if(check.equals("1")) {
				resultMap.put("status", 2);
			}else if(check.equals("2")) {
				resultMap.put("status", 3);
			}else if(check.equals("0")) {
				String pwd = param.get("pwd").toString();
				String pwd2 = param.get("pwd2").toString();
				if(pwd.length() < 4) {
					resultMap.put("status", 4);
				}else if(pwd.length() >= 4) {
					if(!pwd.equals(pwd2)) {
						resultMap.put("status", 5);
					}else if(pwd.equals(pwd2)){
						param.put("sqlType", "user.sign_up");
						param.put("sql", "insert");
						int status = (int) DI.callDB(param);
						if(status==0) {
							resultMap.put("status", 0);
						}else if(status==1) {
							resultMap.put("status", 1);
						}
					}
				}
			}
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> tryLogin(HttpServletRequest req, HttpSession sess) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("sqlType", "user.tryLogin");
		param.put("sql", "selectOne");
		HashMap<String, Object> tryLogin = (HashMap<String, Object>) DI.callDB(param);
		if(tryLogin == null) {
			resultMap.put("status", 0);
		}else{
			int userNo = (int) tryLogin.get("userNo");
			int failStack = (int) tryLogin.get("failStack");
			tryLogin.put("signDate", new SimpleDateFormat("yyyy MM dd HH mm ss").format(tryLogin.get("signDate")) );
			param.put("userNo", userNo);
			if( !param.get("pwd").equals(tryLogin.get("password"))  ) {
				if(userNo != 1) {
					param.put("sqlType", "user.failStack");
					param.put("sql", "update");
					int stackUpdate = (int) DI.callDB(param);
					if(stackUpdate == 1) {
						resultMap.put("failStack", failStack + 1);
					}
				}else {
					resultMap.put("failStack", "none");
				}
				resultMap.put("status", 3);
			}else if(param.get("pwd").equals(tryLogin.get("password"))) {
				sess.setAttribute("user", tryLogin);
				if(userNo == 1) {
					resultMap.put("status", 2);
				}else{
					String nick = tryLogin.get("nickName").toString();
					param.put("sqlType", "user.resetStack");
					param.put("sql", "update");
					int stackUpdate = (int) DI.callDB(param);
					if(stackUpdate == 1) {
						resultMap.put("failStack", failStack);
					}
					resultMap.put("nick", nick);
					resultMap.put("status", 1);
				}
			}
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> sessionCheck(HttpSession sess) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Object userSession = sess.getAttribute("user");
		resultMap.put("userSession", userSession);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> chID(HttpServletRequest req, HttpSession sess) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		HashMap<String, Object> userSession = (HashMap<String, Object>) sess.getAttribute("user");
		if(userSession==null) {
			resultMap.put("status", 0);
		}else {
			param.put("userNo", userSession.get("userNo").toString() );
			param.put("sql", "update");
			param.put("sqlType", "user.chID");
			int status = (int) DI.callDB(param);
			if(status == 0) {
				resultMap.put("status", 0);
			}else if(status == 1) {
				userSession.put("nickName", param.get("nick") );
				userSession.put("userEmail", param.get("email") );
				sess.setAttribute("user", (Object) userSession );
				resultMap.put("status", 1);
			}
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> chPWD(HttpServletRequest req, HttpSession sess) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		HashMap<String, Object> userSession = (HashMap<String, Object>) sess.getAttribute("user");
		if(userSession==null) {
			resultMap.put("status", 0);
		}else {
			String newPWD = param.get("pwd").toString();
			String originPWD = userSession.get("password").toString();
			if(newPWD.equals(originPWD)) {
				resultMap.put("status", 2);
			}else {
				param.put("userNo", userSession.get("userNo").toString() );
				param.put("sql", "update");
				param.put("sqlType", "user.chPWD");
				int status = (int) DI.callDB(param);
				if(status == 0) {
					resultMap.put("status", 0);
				}else if(status == 1) {
					userSession.put("password", newPWD );
					sess.setAttribute("user", (Object) userSession );
					resultMap.put("status", 1);
				}
			}
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> chGetMail(HttpServletRequest req, HttpSession sess) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		HashMap<String, Object> userSession = (HashMap<String, Object>) sess.getAttribute("user");
		if(userSession==null) {
			resultMap.put("status", 0);
		}else {
			String newGETMAIL = param.get("getMail").toString();
			String originGETMAIL = userSession.get("getMail").toString();
			if(newGETMAIL.equals(originGETMAIL)) {
				resultMap.put("status", 2);
			}else {
				param.put("userNo", userSession.get("userNo").toString() );
				param.put("sql", "update");
				param.put("sqlType", "user.chGetMail");
				int status = (int) DI.callDB(param);
				if(status == 0) {
					resultMap.put("status", 0);
				}else if(status == 1) {
					userSession.put("getMail", newGETMAIL );
					sess.setAttribute("user", (Object) userSession );
					resultMap.put("status", 1);
				}
			}
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> delUser(HttpSession sess) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> userSession = (HashMap<String, Object>) sess.getAttribute("user");
		if(userSession==null) {
			resultMap.put("status", 0);
		}else {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userNo", userSession.get("userNo").toString() );
			param.put("sql", "update");
			param.put("sqlType", "user.delUser");
			int status = (int) DI.callDB(param);
			if(status == 0) {
				resultMap.put("status", 0);
			}else if(status == 1) {
				sess.invalidate();
				resultMap.put("status", 1);
			}
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> userList(HttpServletRequest req) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("limit", Integer.parseInt(param.get("limit").toString()) );
		param.put("sql", "selectList");
		param.put("sqlType", "user.userList");
		List<HashMap<String, Object>> userList = (List<HashMap<String, Object>>) DI.callDB(param);
		for(int u=0; u<userList.size(); u++) {
			userList.get(u).put("signDate", new SimpleDateFormat("yyyy MM dd HH mm ss").format(userList.get(u).get("signDate")) );
		}
		resultMap.put("list", userList);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> userCount() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sql", "selectOne");
		param.put("sqlType", "user.userCount");
		int userCount = (int) DI.callDB(param);
		resultMap.put("count", userCount);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> adminDelUser(HttpServletRequest req) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("sql", "update");
		param.put("sqlType", "user.delUser");
		int status = (int) DI.callDB(param);
		if(status == 0) {
			resultMap.put("status", 0);
		}else if(status == 1) {
			resultMap.put("status", 1);
		}
		return resultMap;
	}

}
