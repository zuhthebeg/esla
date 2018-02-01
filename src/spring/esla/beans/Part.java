package spring.esla.beans;

import java.util.HashMap;

public class Part {
	private String bigPart;
	private String smallPart;
	private String pagePart;
	private String contentPart;
	private String actionPart;
	private String problemPart;
	
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
	@Override
	public String toString() {
		return "Part [bigPart=" + bigPart + ", smallPart=" + smallPart
				+ ", pagePart=" + pagePart + ", contentPart=" + contentPart
				+ ", actionPart=" + actionPart + ", problemPart=" + problemPart
				+ "]";
	}

	public void toMap(HashMap<String , Object> pMap){
		//bigPart == null && smallPart == null  && pagePart == null && contentPart == null &&actionPart == null &&problemPart == null 
		if(bigPart.isEmpty() && smallPart.isEmpty()  && pagePart.isEmpty() && contentPart.isEmpty() &&actionPart.isEmpty() &&problemPart.isEmpty()  ){	
			pMap.put("part", null);
			pMap.put("bigPart", "대단원");
			pMap.put("smallPart", "소단원");
			pMap.put("pagePart", "페이지");
			pMap.put("contentPart", "본문");
			pMap.put("actionPart", "탐구활동");
			pMap.put("problemPart", "문제");
		}else{
			pMap.put("part", 1);
			pMap.put("bigPart", bigPart);
			pMap.put("smallPart", smallPart);
			pMap.put("pagePart", pagePart);
			pMap.put("contentPart", contentPart);
			pMap.put("actionPart", actionPart);
			pMap.put("problemPart", problemPart);
		}
	}
	
}
