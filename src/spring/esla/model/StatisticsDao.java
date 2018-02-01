package spring.esla.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import spring.esla.beans.Board;
import spring.esla.beans.Statistics;
import spring.esla.beans.Tree;


import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("unchecked")
@Repository
public class StatisticsDao extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient){
		super.setSqlMapClient(sqlMapClient);
	}

	// 통계(전문어만)
	public ArrayList<Statistics> getStatistics(HashMap<String, Object> pMap) throws SQLException {
		return  (ArrayList<Statistics>)getSqlMapClient().queryForList("getStatistics" , pMap);
	}


	public ArrayList<Board> getWordDataToExcel(Statistics stat) throws SQLException {
		return  (ArrayList<Board>)getSqlMapClient().queryForList("getWordDataToExcel" , stat);
	}

	public ArrayList<Board> getWordBySearchWord(Statistics stat) throws SQLException {
		return  (ArrayList<Board>)getSqlMapClient().queryForList("getWordBySearchWord" , stat);
	}

	public ArrayList<String> getSentenceByTagIdx(Integer tag_idx) throws SQLException {
		return  (ArrayList<String>)getSqlMapClient().queryForList("getSentenceByTagIdx" , tag_idx);
	}

	public ArrayList<String> getNeighborListByTagIdx(Integer tag_idx) throws SQLException {
		return  (ArrayList<String>)getSqlMapClient().queryForList("getNeighborListByTagIdx" , tag_idx);
	}

	public ArrayList<Statistics> getStaticLevelFourByTagIdx(Integer tag_idx) throws SQLException {
		return  (ArrayList<Statistics>)getSqlMapClient().queryForList("getStaticLevelFourByTagIdx" , tag_idx);
	}

}
