package spring.esla.beans;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class Board {

	private int idx;
	private String writer;
	private String regdate;
	private int count;
	private String content;
	private String regip;
	private String filename;
	private MultipartFile file;
	
	private String title;
	private String process;
	private String grade;
	private String publisher;
	private String chapter;
	private int page;
	private int document_idx;
	
	
	//tags meta infomation
	private int sentence;
	private int word;
	private String tag;
	private String base;
	private String part;
	private String word_class;
	
	private int dic_biology_cnt;
	private int dic_chemistry_cnt;
	private int dic_earth_cnt;
	private int dic_physics_cnt;
	
	private String dic_part;
	
	private Integer	word_no;
	private Integer result_count;
	private Integer result_std_count;
	
	private String tech_term_name;
	
	public void setClearTagMetainfo(){
		//tags meta infomation
		tag = null;
		base = null;
		part = null;
		word_class = null;
		tech_term_name = null;
		
		dic_biology_cnt = 0;
		dic_chemistry_cnt = 0;
		dic_earth_cnt = 0;
		dic_physics_cnt = 0;
		
		dic_part = null;
		
		word_no = 0;
		result_count = 0;
		result_std_count = 0;
	}
	
	
	public Integer getResult_std_count() {
		return result_std_count;
	}


	public void setResult_std_count(Integer result_std_count) {
		this.result_std_count = result_std_count;
	}


	public String getTech_term_name() {
		return tech_term_name;
	}


	public void setTech_term_name(String tech_term_name) {
		this.tech_term_name = tech_term_name;
	}


	public Integer getWord_no() {
		return word_no;
	}

	public void setWord_no(Integer word_no) {
		this.word_no = word_no;
	}

	public Integer getResult_count() {
		return result_count;
	}

	public void setResult_count(Integer result_count) {
		this.result_count = result_count;
	}

	public String getDic_part() {
		return dic_part;
	}

	public void setDic_part(String dic_part) {
		this.dic_part = dic_part;
	}



	public String getWord_class() {
		return word_class;
	}

	public void setWord_class(String word_class) {
		this.word_class = word_class;
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

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public int getDocument_idx() {
		return document_idx;
	}

	public void setDocument_idx(int document_idx) {
		this.document_idx = document_idx;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSentence() {
		return sentence;
	}

	public void setSentence(int sentence) {
		this.sentence = sentence;
	}

	public int getWord() {
		return word;
	}

	public void setWord(int word) {
		this.word = word;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}


	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
