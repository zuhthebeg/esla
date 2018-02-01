package spring.esla.model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import spring.esla.beans.Board;
import spring.esla.beans.Statistics;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class TagsDao  extends SqlMapClientDaoSupport{
	@Resource(name="sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient){
		super.setSqlMapClient(sqlMapClient);
	}
	//태그 삭제
	public void deleteTagInfo(Integer idx) throws SQLException {
		getSqlMapClient().startTransaction();
		
		getSqlMapClient().update("updateTagsWordNum", idx); //삭제될 태그다음 태그들의 단어번호를 -1 해주고
		getSqlMapClient().delete("deleteTagInfo" , idx);	// 태그 삭제
		
		getSqlMapClient().commitTransaction();
		getSqlMapClient().endTransaction();
	}
	
	public void updateTagsWordNumByAddition(Board tag) throws SQLException {
		getSqlMapClient().update("updateTagsWordNumByAddition", tag); 
	}
	
	public void updateTagInfo(Board tag) throws SQLException {
		getSqlMapClient().update("updateTagInfo", tag); 
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchPhyDicByBigramText(	ArrayList<String> bigramArray) throws SQLException {
		return (ArrayList<String>) getSqlMapClient().queryForList("searchPhyDicByBigramText",bigramArray);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchCheDicByBigramText(	ArrayList<String> bigramArray) throws SQLException {
		return (ArrayList<String>) getSqlMapClient().queryForList("searchCheDicByBigramText",bigramArray);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchBioDicByBigramText(	ArrayList<String> bigramArray) throws SQLException {
		return (ArrayList<String>) getSqlMapClient().queryForList("searchBioDicByBigramText",bigramArray);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchEarDicByBigramText(	ArrayList<String> bigramArray) throws SQLException {
		return (ArrayList<String>) getSqlMapClient().queryForList("searchEarDicByBigramText",bigramArray);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchPhyDicByText(String text) throws SQLException {
		return (ArrayList<String>) getSqlMapClient().queryForList("searchPhyDicByText",text);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchCheDicByText(String text) throws SQLException {
		return (ArrayList<String>) getSqlMapClient().queryForList("searchCheDicByText",text);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchBioDicByText(String text) throws SQLException {
		return (ArrayList<String>) getSqlMapClient().queryForList("searchBioDicByText",text);
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchEarDicByText(String text) throws SQLException {
		return (ArrayList<String>) getSqlMapClient().queryForList("searchEarDicByText",text);
	}
	public void updateWordNoByTagIdx(Board tag)  throws SQLException {
		getSqlMapClient().update("updateWordNoByTagIdx", tag); 
	}
	
}
