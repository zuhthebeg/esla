package spring.esla.model;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import spring.esla.beans.Definition;
import spring.esla.beans.KorDictionaryVO;
import spring.esla.beans.Speechpart;


import com.ibatis.sqlmap.client.SqlMapClient;


@Repository
public class DictionaryDao extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient){
		super.setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	public void insertDefinitionDataByXml(Definition lTempDefinition) throws SQLException{
		getSqlMapClient().insert("insertDefinitionDataByXml", lTempDefinition );
	}

	public void insertSpeechpartDataByXml(Speechpart speechpart) throws SQLException{
		getSqlMapClient().insert("insertSpeechpartDataByXml", speechpart );
	}

	public void insertCodeinfoDataByXml(Speechpart lTempSpeechpart)  throws SQLException{
		getSqlMapClient().insert("insertCodeinfoDataByXml", lTempSpeechpart );
	}

	@SuppressWarnings("unchecked")
	public ArrayList<KorDictionaryVO> getDictionaryResult(String baseWord)  throws SQLException{
		return (ArrayList<KorDictionaryVO>)getSqlMapClient().queryForList("getDictionaryResult", baseWord);
	}

	public KorDictionaryVO getKorDicWordNominee(String baseWord) throws SQLException{
		return (KorDictionaryVO)getSqlMapClient().queryForObject("getKorDicWordNominee", baseWord);
	}	
	
	public Integer getKorDicWordCount(String baseWord) throws SQLException{
		return (Integer)getSqlMapClient().queryForObject("getKorDicWordCount", baseWord);
	}		
	public Integer getKorDicWordCountIsNull(String baseWord) throws SQLException{
		return (Integer)getSqlMapClient().queryForObject("getKorDicWordCountIsNull", baseWord);
	}		
	
}
