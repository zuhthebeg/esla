package spring.esla.beans;

public class Definition {
private int			word_no;
private int			sp_no;
private int			ptrn_no;
private int			dfn_no;
private int			dfn_seq;
private String		pattern;
private String		grammar;
private String		word_grp_code;
private String		tech_term_code;
private String		definition;
private String		example;
private String		spelling_rule;
private String		addition;

private String	code_type;
public String getCode_type() {
	return code_type;
}
public void setCode_type(String code_type) {
	this.code_type = code_type;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getCode_name() {
	return code_name;
}
public void setCode_name(String code_name) {
	this.code_name = code_name;
}
private String	code;
private String	code_name;

public int getWord_no() {
	return word_no;
}
public void setWord_no(int word_no) {
	this.word_no = word_no;
}
public int getSp_no() {
	return sp_no;
}
public void setSp_no(int sp_no) {
	this.sp_no = sp_no;
}
public int getPtrn_no() {
	return ptrn_no;
}
public void setPtrn_no(int ptrn_no) {
	this.ptrn_no = ptrn_no;
}
public int getDfn_no() {
	return dfn_no;
}
public void setDfn_no(int dfn_no) {
	this.dfn_no = dfn_no;
}
public int getDfn_seq() {
	return dfn_seq;
}
public void setDfn_seq(int dfn_seq) {
	this.dfn_seq = dfn_seq;
}
public String getPattern() {
	return pattern;
}
public void setPattern(String pattern) {
	this.pattern = pattern;
}
public String getGrammar() {
	return grammar;
}
public void setGrammar(String grammar) {
	this.grammar = grammar;
}
public String getWord_grp_code() {
	return word_grp_code;
}
public void setWord_grp_code(String word_grp_code) {
	this.word_grp_code = word_grp_code;
}
public String getTech_term_code() {
	return tech_term_code;
}
public void setTech_term_code(String tech_term_code) {
	this.tech_term_code = tech_term_code;
}
public String getDefinition() {
	return definition;
}
public void setDefinition(String definition) {
	this.definition = definition;
}
public String getExample() {
	return example;
}
public void setExample(String example) {
	this.example = example;
}
public String getSpelling_rule() {
	return spelling_rule;
}
public void setSpelling_rule(String spelling_rule) {
	this.spelling_rule = spelling_rule;
}
public String getAddition() {
	return addition;
}
public void setAddition(String addition) {
	this.addition = addition;
}
@Override
public String toString() {
	return "Defination [word_no=" + word_no + ", sp_no=" + sp_no + ", ptrn_no="
			+ ptrn_no + ", dfn_no=" + dfn_no + ", dfn_seq=" + dfn_seq
			+ ", pattern=" + pattern + ", grammar=" + grammar
			+ ", word_grp_code=" + word_grp_code + ", tech_term_code="
			+ tech_term_code + ", definition=" + definition + ", example="
			+ example + ", spelling_rule=" + spelling_rule + ", addition="
			+ addition + "]";
}
	

}
