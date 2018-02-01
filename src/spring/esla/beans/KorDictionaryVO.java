package spring.esla.beans;

public class KorDictionaryVO {
	private int	word_no;
	private String upper_word;
	private String word;
	private String tech_term_name;
	private String sp_name;
	private String definition;
	
	

	@Override
	public String toString() {
		return "KorDictionaryVO [word_no=" + word_no + ", upper_word="
				+ upper_word + ", word=" + word + ", tech_term_name="
				+ tech_term_name + ", sp_name=" + sp_name + ", definition="
				+ definition + "]";
	}
	public String getUpper_word() {
		return upper_word;
	}
	public void setUpper_word(String upper_word) {
		this.upper_word = upper_word;
	}
	public int getWord_no() {
		return word_no;
	}
	public void setWord_no(int word_no) {
		this.word_no = word_no;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getTech_term_name() {
		return tech_term_name;
	}
	public void setTech_term_name(String tech_term_name) {
		this.tech_term_name = tech_term_name;
	}
	public String getSp_name() {
		return sp_name;
	}
	public void setSp_name(String sp_name) {
		this.sp_name = sp_name;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	
}
