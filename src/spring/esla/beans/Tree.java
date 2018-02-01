package spring.esla.beans;
// for document
public class Tree {
	private int idx;
	private String val;
	private String title;
	private String isFolder;
	private String key;
	private String expand;
	private String isLazy;
	private String name;
	private int par_idx;
	
	private String is_leaf;
	private String level;
	
	private int class_order;
	private int swap_idx;
	

	public int getSwap_idx() {
		return swap_idx;
	}
	public void setSwap_idx(int swap_idx) {
		this.swap_idx = swap_idx;
	}
	public int getClass_order() {
		return class_order;
	}
	public void setClass_order(int class_order) {
		this.class_order = class_order;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getPar_idx() {
		return par_idx;
	}
	public void setPar_idx(int par_idx) {
		this.par_idx = par_idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIs_leaf() {
		return is_leaf;
	}
	public void setIs_leaf(String is_leaf) {
		this.is_leaf = is_leaf;
	}

	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
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
	public String getIsFolder() {
		return isFolder;
	}
	public void setIsFolder(String isFolder) {
		this.isFolder = isFolder;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	public String getIsLazy() {
		return isLazy;
	}
	public void setIsLazy(String isLazy) {
		this.isLazy = isLazy;
	}
	
	
}
