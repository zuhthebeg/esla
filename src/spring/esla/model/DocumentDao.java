package spring.esla.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import spring.esla.beans.Board;
import spring.esla.beans.Tree;


import com.ibatis.sqlmap.client.SqlMapClient;


@Repository
public class DocumentDao extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient){
		super.setSqlMapClient(sqlMapClient);
	}
	
	//데이터가 들어있는 년도 가져오는 함수
	@SuppressWarnings("unchecked")
	public ArrayList<Tree> getProcessNodes() throws SQLException{
		return  (ArrayList<Tree>)getSqlMapClient().queryForList("getProcessNodes" );
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Tree> getChildNodeByIdx(Integer idx) throws SQLException {
		return  (ArrayList<Tree>)getSqlMapClient().queryForList("getChildNodeByIdx" ,idx);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Board> getPageListByDocumentIdx(Integer idx) throws SQLException {
		return  (ArrayList<Board>)getSqlMapClient().queryForList("getPageListByDocumentIdx", idx );
	}
	public Tree getMetainfoByDocumentIdx(Integer idx) throws SQLException {
		return  (Tree)getSqlMapClient().queryForObject("getMetainfoByDocumentIdx", idx );
	}

	public Integer getPageCountByDocumentIdx(Integer doc_idx) throws SQLException {
		return  (Integer)getSqlMapClient().queryForObject("getPageCountByDocumentIdx", doc_idx );
	}

	public Tree getTitleByDocumentIdx(Integer doc_idx) throws SQLException {
		return  (Tree)getSqlMapClient().queryForObject("getTitleByDocumentIdx", doc_idx );
	}
	public Tree getPublisherByDocumentIdx(Integer doc_idx) throws SQLException {
		return  (Tree)getSqlMapClient().queryForObject("getPublisherByDocumentIdx", doc_idx );
	}
	public Tree getGradeByDocumentIdx(Integer doc_idx) throws SQLException {
		return  (Tree)getSqlMapClient().queryForObject("getGradeByDocumentIdx", doc_idx );
	}
	public Tree getProcessByDocumentIdx(Integer doc_idx) throws SQLException {
		return  (Tree)getSqlMapClient().queryForObject("getProcessByDocumentIdx", doc_idx );
	}
	
	public void updateNodeByIdx(Tree node) throws SQLException {
		getSqlMapClient().update("updateNodeByIdx", node);
	}

	public void insertChildNode(Tree node) throws SQLException {
		getSqlMapClient().insert("insertChildNode", node);
	}

	public int getDictionaryCountByBaseCount(String baseWord, String table) throws SQLException {
		HashMap<String, String > map = new HashMap<String, String>();
		map.put("baseWord", baseWord);
		map.put("table", table);
		return (Integer)getSqlMapClient().queryForObject("getDictionaryCountByBaseCount", map);
	}

	public void deleteNodeAndChild(Integer idx) throws SQLException {
		getSqlMapClient().startTransaction();
		
		getSqlMapClient().delete("deleteArticleByIdx", idx);			// 최하위 노드 삭제 - tag들은 연쇄삭제됨
		getSqlMapClient().delete("deleteNodeAndChild", idx);	// 트리 노드 삭제		
		
		getSqlMapClient().commitTransaction();
		getSqlMapClient().endTransaction();
		

		
	}

	public void swapNodeOrder(Tree node) throws SQLException {
		getSqlMapClient().startTransaction();
		
		Tree swapNode = new Tree();
		swapNode.setClass_order(node.getClass_order());
		swapNode.setIdx(node.getSwap_idx());
		
		getSqlMapClient().update("swapNodeOrder", node);
		
		getSqlMapClient().update("updateNodeOrder", swapNode);
		
		getSqlMapClient().commitTransaction();
		getSqlMapClient().endTransaction();
		
	}

	public void updateNodeOrder(Tree node) throws SQLException {
		getSqlMapClient().update("updateNodeOrder", node);
	}
	
	public String getTechTermNamesByWordNo(Integer word_no) throws SQLException {
		return (String) getSqlMapClient().queryForObject("getTechTermNamesByWordNo",word_no);
	}

}
