package spring.esla.beans;

import java.util.HashMap;

public class Statistics {
	private String tag;
	private String part;
	private String path;
	private String isAllWord;
	
	private int dic_biology_cnt;
	private int dic_chemistry_cnt;
	private int dic_earth_cnt;
	private int dic_physics_cnt;
	private int word_no;
	
	private int cnt;
	
	private int doc_idx;
	
	private Integer st_phy;
	private Integer st_bio;
	private Integer st_che;
	private Integer st_ear;
	
	private Integer ex_phy;
	private Integer ex_bio;
	private Integer ex_che;
	private Integer ex_ear;
	
	
	
	private String bigPart;
	private String smallPart;
	private String pagePart;
	private String contentPart;
	private String actionPart;
	private String problemPart;
	
	
	public String getIsAllWord() {
		return isAllWord;
	}
	public void setIsAllWord(String isAllWord) {
		this.isAllWord = isAllWord;
	}
	public String getBigPart() {
		return bigPart;
	}
	public void setBigPart(String bigPart) {
		this.bigPart = bigPart;
	}
	public String getSmallPart() {
		return smallPart;
	}
	public void setSmallPart(String smallPart) {
		this.smallPart = smallPart;
	}
	public String getPagePart() {
		return pagePart;
	}
	public void setPagePart(String pagePart) {
		this.pagePart = pagePart;
	}
	public String getContentPart() {
		return contentPart;
	}
	public void setContentPart(String contentPart) {
		this.contentPart = contentPart;
	}
	public String getActionPart() {
		return actionPart;
	}
	public void setActionPart(String actionPart) {
		this.actionPart = actionPart;
	}
	public String getProblemPart() {
		return problemPart;
	}
	public void setProblemPart(String problemPart) {
		this.problemPart = problemPart;
	}

	public void partCheck(){
		if(bigPart == null && smallPart == null  && pagePart == null && contentPart == null &&actionPart == null &&problemPart == null   ){	
			part=null;
			bigPart="대단원";
			smallPart="소단원";
			pagePart="페이지";
			contentPart="본문";
			actionPart="탐구활동";
			problemPart="문제";
		}else{
			part="1";
			if(bigPart.equals( "1") )		bigPart="대단원";
			if(smallPart.equals( "1") )		smallPart="소단원";
			if(pagePart.equals( "1") )	pagePart="페이지";
			if(contentPart.equals( "1") )	contentPart="본문";
			if(actionPart.equals( "1") )		actionPart="탐구활동";
			if(problemPart.equals( "1") )		problemPart="문제";
		}
	}
	
	
	@Override
	public String toString() {
		return "Statistics [tag=" + tag + ", part=" + part
				+ ", dic_biology_cnt=" + dic_biology_cnt
				+ ", dic_chemistry_cnt=" + dic_chemistry_cnt
				+ ", dic_earth_cnt=" + dic_earth_cnt + ", dic_physics_cnt="
				+ dic_physics_cnt + ", word_no=" + word_no + ", cnt=" + cnt
				+ ", doc_idx=" + doc_idx + ", st_phy=" + st_phy + ", st_bio="
				+ st_bio + ", st_che=" + st_che + ", st_ear=" + st_ear
				+ ", ex_phy=" + ex_phy + ", ex_bio=" + ex_bio + ", ex_che="
				+ ex_che + ", ex_ear=" + ex_ear + ", getPart()=" + getPart()
				+ ", getDoc_idx()=" + getDoc_idx() + ", getSt_phy()="
				+ getSt_phy() + ", getSt_bio()=" + getSt_bio()
				+ ", getSt_che()=" + getSt_che() + ", getSt_ear()="
				+ getSt_ear() + ", getEx_phy()=" + getEx_phy()
				+ ", getEx_bio()=" + getEx_bio() + ", getEx_che()="
				+ getEx_che() + ", getEx_ear()=" + getEx_ear()
				+ ", getWord_no()=" + getWord_no() + ", getTag()=" + getTag()
				+ ", getDic_biology_cnt()=" + getDic_biology_cnt()
				+ ", getDic_chemistry_cnt()=" + getDic_chemistry_cnt()
				+ ", getDic_earth_cnt()=" + getDic_earth_cnt()
				+ ", getDic_physics_cnt()=" + getDic_physics_cnt()
				+ ", getCnt()=" + getCnt() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getPart() {
		return part;
	}


	public void setPart(String part) {
		this.part = part;
	}
	
	public int getDoc_idx() {
		return doc_idx;
	}


	public void setDoc_idx(int doc_idx) {
		this.doc_idx = doc_idx;
	}


	public Integer getSt_phy() {
		return st_phy;
	}
	public void setSt_phy(Integer st_phy) {
		this.st_phy = st_phy;
	}
	public Integer getSt_bio() {
		return st_bio;
	}
	public void setSt_bio(Integer st_bio) {
		this.st_bio = st_bio;
	}
	public Integer getSt_che() {
		return st_che;
	}
	public void setSt_che(Integer st_che) {
		this.st_che = st_che;
	}
	public Integer getSt_ear() {
		return st_ear;
	}
	public void setSt_ear(Integer st_ear) {
		this.st_ear = st_ear;
	}
	public Integer getEx_phy() {
		return ex_phy;
	}
	public void setEx_phy(Integer ex_phy) {
		this.ex_phy = ex_phy;
	}
	public Integer getEx_bio() {
		return ex_bio;
	}
	public void setEx_bio(Integer ex_bio) {
		this.ex_bio = ex_bio;
	}
	public Integer getEx_che() {
		return ex_che;
	}
	public void setEx_che(Integer ex_che) {
		this.ex_che = ex_che;
	}
	public Integer getEx_ear() {
		return ex_ear;
	}
	public void setEx_ear(Integer ex_ear) {
		this.ex_ear = ex_ear;
	}
	public int getWord_no() {
		return word_no;
	}
	public void setWord_no(int word_no) {
		this.word_no = word_no;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getDic_biology_cnt() {
		return dic_biology_cnt;
	}
	public void setDic_biology_cnt(int dic_biology_cnt) {
		this.dic_biology_cnt = dic_biology_cnt;
	}
	public int getDic_chemistry_cnt() {
		return dic_chemistry_cnt;
	}
	public void setDic_chemistry_cnt(int dic_chemistry_cnt) {
		this.dic_chemistry_cnt = dic_chemistry_cnt;
	}
	public int getDic_earth_cnt() {
		return dic_earth_cnt;
	}
	public void setDic_earth_cnt(int dic_earth_cnt) {
		this.dic_earth_cnt = dic_earth_cnt;
	}
	public int getDic_physics_cnt() {
		return dic_physics_cnt;
	}
	public void setDic_physics_cnt(int dic_physics_cnt) {
		this.dic_physics_cnt = dic_physics_cnt;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	
}
