package com.java.web.user;

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
		
		return null;
	}

	@Override
	public HashMap<String, Object> sessionCheck(HttpSession sess) {
		
		return null;
	}

	@Override
	public String getUserNo(HttpSession sess) {
		
		return null;
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
