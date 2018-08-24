package com.java.web.dao;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.java.web.user.UserService;

@Repository
public class Dao implements DaoInterface {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Resource(name="sqlSession")
	SqlSession sess;

	@Override
	public Object callDB(HashMap<String, Object> param) {
		String sql = param.get("sql").toString();
		String sqlType = param.get("sqlType").toString();
		logger.info("@ DAO LOG : " + sql + " > " + sqlType);
		if ("selectOne".equals(sql)) {
			return sess.selectOne(sqlType, param);
		} else if ("insert".equals(sql)) {
			return sess.insert(sqlType, param);
		} else if ("update".equals(sql)) {
			return sess.update(sqlType, param);
		} else if ("selectList".equals(sql)) {
			return sess.selectList(sqlType, param);
		}
		return null;
	}

}
