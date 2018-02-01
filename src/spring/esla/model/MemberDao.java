package spring.esla.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import spring.esla.beans.Member;
import spring.esla.beans.Statistics;


import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("unchecked")
@Repository
public class MemberDao extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient){
		super.setSqlMapClient(sqlMapClient);
	}

	// 통계(전문어만)
	public ArrayList<Statistics> getStatistics(HashMap<String, Object> pMap) throws SQLException {
		return  (ArrayList<Statistics>)getSqlMapClient().queryForList("getStatistics" , pMap);
	}

	public Member getUserByAccount(Member user) throws SQLException {
		return  (Member)getSqlMapClient().queryForObject("getUserByAccount" , user);
	}

	public void insertUserInfo(Member user) throws SQLException{
		getSqlMapClient().insert("insertUserInfo", user);
	}
	
	public void updateUserPassword(Member user) throws SQLException{
		getSqlMapClient().update("updateUserPassword", user);
	}
}
