package com.footballer.rest.api.v2.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v2.vo.WechatConfig;


@Service("mybatisBaseDao")
public class MybatisBaseDao {
	private static final Logger logger = LoggerFactory
			.getLogger(MybatisBaseDao.class);
	@Autowired
	protected SqlSession sqlSession;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Object selectOne(String sqlId, Object obj){
		try {
			if (obj == null) {
				return sqlSession.selectOne(sqlId);
			} else {
				return sqlSession.selectOne(sqlId, obj);
			}
		} catch (Exception e) {
			logger.error("select one error", e);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<?> selectList(String sqlId, Object obj, RowBounds row) {
		try {
			return sqlSession.selectList(sqlId, obj, row);
		} catch (Exception e) {
			logger.error("select list error", e);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<?> selectList(String sqlId, Object obj){
		try {
			if (obj == null) {
				return sqlSession.selectList(sqlId);
			} else {
				return sqlSession.selectList(sqlId, obj);
			}
		} catch (Exception e) {
			logger.error("select list error", e);
		}
		
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public int delete(String sqlId, Object obj) {
		try {
			if (obj == null) {
				return sqlSession.delete(sqlId);
			} else {
				return sqlSession.delete(sqlId, obj);
			}
		} catch (Exception e) {
			logger.error("delete error", e);
			return 0;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public int insert(String sqlId, Object obj)  {
		if (obj == null) {
			return 0;
		} else {
			return sqlSession.insert(sqlId, obj);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Object insertReturnWithParameter(String sqlId, Object obj)  {
		sqlSession.insert(sqlId, obj);
		return obj;
	}
	
	
	/**通过Mybatis 的 prepareCall 去call SP 来完成 读取mysql SP 多个结果集的尝试  失败
	 * 
	 * 未走通 目前遇到问题是 Druid 报错com.alibaba.druid.sql.parser.ParserException: syntax error xxx, error in xxxx
	   但是检查数据是正确得。。因为暂时不想换 数据源，所以考虑第二套方案 使用临时表
	 * @param procedureSql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	@Deprecated
	public List<?> selectMutipleResultList(String procedureSql, Map<String, Object> params) throws SQLException{
		
//		SqlSessionTemplate st = (SqlSessionTemplate) sqlSession;
//
//		Connection connection = SqlSessionUtils.getSqlSession(
//		                st.getSqlSessionFactory(), st.getExecutorType(),
//		                st.getPersistenceExceptionTranslator()).getConnection();
		
		//未走通 目前遇到问题是 Druid 报错com.alibaba.druid.sql.parser.ParserException: syntax error xxx, error in xxxx
		//但是检查数据是正确得。。因为暂时不想换 数据源，所以考虑第二套方案 使用临时表
		String arenaId = "52";
		
		CallableStatement callableSt = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection().prepareCall(procedureSql);
		callableSt.setString("arenaId", arenaId);
		
		// Call Stored Procedure
		boolean results = callableSt.execute();
		
		while (results) {
			ResultSet rs = callableSt.getResultSet();
			 ResultSetMetaData rsmd = rs.getMetaData();
			 String tableName = "";
			// Display the column name and type.
		      int cols = rsmd.getColumnCount();
		      for (int i = 1; i <= cols; i++) {
		         System.out.println("NAME: " + rsmd.getColumnName(i) + " " + "TYPE: " + rsmd.getColumnTypeName(i));	
		         tableName = rsmd.getTableName(i);
		         
		      }
		      while (rs.next()) {
				if (tableName.equals("person")) {
					System.out.println("Table name is: "+tableName);
				} else if (tableName.equals("employee")) {
					System.out.println("Table name is: "+tableName);
				}else {
					System.out.println("Table name is: "+tableName);
				}
				
		      }
		     rs.close();				

	        //Check for next result set
	        results = callableSt.getMoreResults();
		}			
		
		return null;
	}
	

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public int update(String sqlId, Object obj) {
		try {
			if (obj == null) {
				return sqlSession.update(sqlId);
			} else {
				return sqlSession.update(sqlId, obj);
			}
		} catch (Exception e) {
			logger.error("update error", e);
			return 0;
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Map<Object, Object> selectMap(String sqlId, Object obj,
			String mapKey, RowBounds rowBounds){
		try {
			if (obj == null) {
				return this.selectMap(sqlId, mapKey);
			}
			return sqlSession.selectMap(sqlId, obj, mapKey, rowBounds);
		} catch (Exception e) {
			logger.error("select Map error", e);
		}
		
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Map<Object, Object> selectMap(String sqlId, Object obj, String mapKey){
		try {
			if (obj == null) {
				return this.selectMap(sqlId, mapKey);
			}
			return sqlSession.selectMap(sqlId, obj, mapKey);
		} catch (Exception e) {
			logger.error("select Map error", e);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Map<Object, Object> selectMap(String sqlId, String mapKey){
		try {
			return sqlSession.selectMap(sqlId, mapKey);
		} catch (Exception e) {
			logger.error("select Map error", e);
		}
		return null;
	}
}
