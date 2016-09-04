package com.era.community.wiki.ui.dto; 


public class WikiCompareDto
{    
	private int first;
	private int second;
	private String secHeader;
	private String comparisonText;
	private boolean compared;
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public String getComparisonText() {
		return comparisonText;
	}
	public void setComparisonText(String comparisonText) {
		this.comparisonText = comparisonText;
	}
	public boolean isCompared() {
		return compared;
	}
	public void setCompared(boolean compared) {
		this.compared = compared;
	}
	public String getSecHeader() {
		return secHeader;
	}
	public void setSecHeader(String secHeader) {
		this.secHeader = secHeader;
	}

}