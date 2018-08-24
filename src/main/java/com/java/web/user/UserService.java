package com.java.web.user;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.dao.DaoInterface;
import com.java.web.util.HttpUtil;

@Service
public class UserService implements UserServiceInterface {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	DaoInterface DI;

	@Override
	public HashMap<String, Object> sign_up(HttpServletRequest req) {
		logger.info("@ SERVICE LOG : VALUE = sign_up");
		logger.info("***************************************************");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		logger.info(" 1) 이메일 & 닉네임 중복 체크.");
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		logger.info(" 2) 이메일 & 닉네임 중복 체크와 회원가입용 파라미터 : " + param);
		param.put("sqlType", "user.idCheck");
		param.put("sql", "selectOne");
		HashMap<String, Object> idCheck = (HashMap<String, Object>) DI.callDB(param);
		logger.info(" 3) 이메일 & 닉네임 중복 체크 결과 : " + idCheck);
		if(idCheck == null) {
			logger.info(" 4) SQL문 실패. 종료");
			resultMap.put("status", 0);
		}else{
			String check = idCheck.get("check").toString();
			if(check.equals("1")) {
				logger.info(" 4) 이메일 중복. 종료");
				resultMap.put("status", 2);
			}else if(check.equals("2")) {
				logger.info(" 4) 닉네임 중복. 종료");
				resultMap.put("status", 3);
			}else if(check.equals("0")) {
				logger.info(" 4) 중복 없음, 중복 체크 완료. 비밀번호 입력 체크");
				String pwd = param.get("pwd").toString();
				String pwd2 = param.get("pwd2").toString();
				if(pwd.length() < 4) {
					logger.info(" 5) 비밀번호 4자리 미만. 종료");
					resultMap.put("status", 4);
				}else if(pwd.length() >= 4) {
					if(!pwd.equals(pwd2)) {
						logger.info(" 5) 비밀번호 재입력 불일치. 종료");
						resultMap.put("status", 5);
					}else if(pwd.equals(pwd2)){
						logger.info(" 5) 비밀번호 체크 완료. 회원가입 시도");
						param.put("sqlType", "user.sign_up");
						param.put("sql", "insert");
						int status = (int) DI.callDB(param);
						logger.info(" 6) 회원가입 결과 : " + status);
						if(status==0) {
							logger.info(" 7) 회원가입 실패.");
							resultMap.put("status", 0);
						}else if(status==1) {
							logger.info(" 7) 회원가입 성공.");
							resultMap.put("status", 1);
						}
					}
				}
			}
		}
		logger.info("***************************************************");
		return resultMap;
	}

	@Override
	public HashMap<String, Object> tryLogin(HttpServletRequest req, HttpSession sess) {
		logger.info("@ SERVICE LOG : VALUE = tryLogin");
		logger.info("***************************************************");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		logger.info(" 1) 로그인 시도.");
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		logger.info(" 2) 로그인 파라미터 : " + param);
		param.put("sqlType", "user.tryLogin");
		param.put("sql", "selectOne");
		HashMap<String, Object> tryLogin = (HashMap<String, Object>) DI.callDB(param);
		logger.info(" 3) 로그인 시도 결과 : " + tryLogin);
		if(tryLogin == null) {
			logger.info(" 4) 로그인 시도 실패. 종료");
			resultMap.put("status", 0);
		}else{
			logger.info(" 4) 로그인 SQL문 결과 있음.");
			int userNo = (int) tryLogin.get("userNo");
			int failStack = (int) tryLogin.get("failStack");
			tryLogin.put("signDate", new SimpleDateFormat("yyyy MM dd HH mm ss").format(tryLogin.get("signDate")) );
			param.put("userNo", userNo);
			if( !param.get("pwd").equals(tryLogin.get("password"))  ) {
				logger.info(" 5) 입력된 비밀번호와 id의 비밀번호 불일치.");
				if(userNo != 1) {
					param.put("sqlType", "user.failStack");
					param.put("sql", "update");
					int stackUpdate = (int) DI.callDB(param);
					logger.info(" 6) 비밀번호 실패횟수 업데이트 결과 : " + stackUpdate);
					if(stackUpdate == 1) {
						resultMap.put("failStack", failStack + 1);
					}
				}else {
					resultMap.put("failStack", "none");
				}
				resultMap.put("status", 3);
			}else if(param.get("pwd").equals(tryLogin.get("password"))) {
				logger.info(" 5) 입력된 비밀번호와 id의 비밀번호 일치.");
				sess.setAttribute("user", tryLogin);
				if(userNo == 1) {
					logger.info(" 6) 관리자 로그인.");
					resultMap.put("status", 2);
				}else{
					String nick = tryLogin.get("nickName").toString();
					logger.info(" 6) 회원명 " + nick + " 님 로그인.");
					param.put("sqlType", "user.resetStack");
					param.put("sql", "update");
					int stackUpdate = (int) DI.callDB(param);
					logger.info(" 7) 비밀번호 실패횟수 업데이트 결과 : " + stackUpdate);
					if(stackUpdate == 1) {
						resultMap.put("failStack", failStack);
					}
					resultMap.put("nick", nick);
					resultMap.put("status", 1);
				}
			}
		}
		logger.info("***************************************************");
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
	public HashMap<String, Object> chID(HttpServletRequest req, HttpSession session) {
		
		return null;
	}

	@Override
	public HashMap<String, Object> chPWD(HttpServletRequest req, HttpSession session) {
		
		return null;
	}

	@Override
	public HashMap<String, Object> chGetMail(HttpServletRequest req, HttpSession session) {
		
		return null;
	}

	@Override
	public HashMap<String, Object> delUser(HttpSession session) {
		
		return null;
	}

}
