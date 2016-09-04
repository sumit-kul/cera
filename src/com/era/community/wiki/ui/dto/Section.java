package com.era.community.wiki.ui.dto; 

public class Section {
	private String header = "";
	private String body = "";
	private int sectionId;
	private int sectionSeq;
		
	public int getSectionId() {
		return sectionId;
	}
	
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	
	public String getHeader() {
		return header;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	public int getSectionSeq() {
		return sectionSeq;
	}

	public void setSectionSeq(int sectionSeq) {
		this.sectionSeq = sectionSeq;
	}
}