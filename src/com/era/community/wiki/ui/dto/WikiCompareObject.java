package com.era.community.wiki.ui.dto; 


public class WikiCompareObject
{    
	private int sectionId;
	private String latestHeader;
	private String latestBody;
	private String prevHeader;
	private String prevstBody;
	
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public String getLatestHeader() {
		return latestHeader;
	}
	public void setLatestHeader(String latestHeader) {
		this.latestHeader = latestHeader;
	}
	public String getLatestBody() {
		return latestBody;
	}
	public void setLatestBody(String latestBody) {
		this.latestBody = latestBody;
	}
	public String getPrevHeader() {
		return prevHeader;
	}
	public void setPrevHeader(String prevHeader) {
		this.prevHeader = prevHeader;
	}
	public String getPrevstBody() {
		return prevstBody;
	}
	public void setPrevstBody(String prevstBody) {
		this.prevstBody = prevstBody;
	}
}