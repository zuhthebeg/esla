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
public class BoardDao extends SqlMapClientDaoSupport{
	
	@Resource(name="sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient){
		super.setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Board> getArticleList(int page) throws SQLException  {
		return  (ArrayList<Board>)getSqlMapClient().queryForList("getArticleList", null, page, 10 );
	}

	public Board getArticle(int idx) throws SQLException {
		return  (Board)getSqlMapClient().queryForObject("getArticle", idx );
	}

	public void deleteArticle(int idx) throws SQLException {
		getSqlMapClient().delete("deleteArticle", idx);
	}

	public Integer insertArticle(Board article) throws SQLException {
		return (Integer)getSqlMapClient().insert("insertArticle", article);
	}

	public void setArticleCount(Board article) throws SQLException {
		getSqlMapClient().update("setArticleCount", article);
	}

	public void insertTags(Board metainfo) throws SQLException {
		getSqlMapClient().insert("insertTags", metainfo);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Board> getTagsByIdx(Integer idx) throws SQLException {
		return (ArrayList<Board>)getSqlMapClient().queryForList("getTagsByIdx", idx);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Board> getTagsByDocumentIdxWithMetainfo(Integer doc_idx) throws SQLException {
		return (ArrayList<Board>)getSqlMapClient().queryForList("getTagsByDocumentIdxWithMetainfo", doc_idx);
	}
	
}
